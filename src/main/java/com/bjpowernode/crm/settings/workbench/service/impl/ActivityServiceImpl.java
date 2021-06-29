package com.bjpowernode.crm.settings.workbench.service.impl;

import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import com.bjpowernode.crm.settings.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.settings.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.settings.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.settings.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<Activity> list(Activity activity) {
        /*
        模糊查询（用户根据前端页面有选择性的输入数据）
        new一个example对象，里面传需要查询的类的字节码（类.class）
        */
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();

        //判断市场活动名称，模糊查询
        if (activity.getName() != null && activity.getName() != "") {
            criteria.andLike("name", "%" + activity.getName() + "%");
        }
        //判断开始日期
        if (activity.getStartDate() != null && activity.getStartDate() != "") {
            criteria.andGreaterThanOrEqualTo("startDate", activity.getStartDate());
        }
        //判断结束日期
        if (activity.getEndDate() != null && activity.getEndDate() != "") {
            criteria.andLessThanOrEqualTo("startDate", activity.getEndDate());
        }
        //所有者模糊查询
        if (activity.getOwner() != null && activity.getOwner() != "") {
            //先去用户表中模糊查询(根据用户输入的内容查出用户表的id)，
            // 然后去市场活动表根据用户表的id（就是市场表的外键owner）所对应的全部信息
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("name", "%" + activity.getOwner() + "%");
            //返回的是是一个用户对象，有用户对象了就能通过用户得到用户的 Id，也就是市场表的owner外键，然后根据id有andIn方法，
            List<User> users = userMapper.selectByExample(example1);
            //集合里面放到是用户的id，应为andIn方法第二个参数是一个集合，所以把id放到集合里
            List<String> uids = new ArrayList<>();
            //for循环遍历集合快捷键 iter
            for (User user : users) {
                uids.add(user.getId());
            }
            //Example.Criteria
            criteria.andIn("owner", uids);
        }


        List<Activity> activities = activityMapper.selectByExample(example);

        //List<Activity> activities = activityMapper.selectAll();
        for (Activity activity1 : activities) {
            User user = userMapper.selectByPrimaryKey(activity1.getOwner());
            activity1.setOwner(user.getName());
        }
        return activities;
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAll();

    }

    @Override
    public ResultVo creatOrUpDate(Activity activity, String name) {
        ResultVo resultVo = new ResultVo();
        //判断添加还是修改，看是否有主键
        if (activity.getId() == null) {     //添加操作
            //设置主键
            activity.setId(UUIDUtil.getUUID());
            //设置创建时间
            activity.setCreateTime(DateTimeUtil.getSysTime());
            //创建者
            activity.setCreateBy(name);

            //这个方法只插入非空字段，用户没输入的就不插入
            //把从前端传过来的用户信息设置到数据库中
            int count = activityMapper.insertSelective(activity);
            if (count == 0) {
                //这个异常在控制层的try，catch里面捕捉，也就是方法返回值是void前端也能知道是否添加成功
                throw new CrmException(CrmEnum.ACTIVITY_CREAT);
            }
            resultVo.setMessage("添加市场活动成功");
        } else {
            activity.setEditBy(name);
            activity.setCreateTime(DateTimeUtil.getSysTime());
            int count = activityMapper.updateByPrimaryKeySelective(activity);
            if (count == 0) {
                //这个异常在控制层的try，catch里面捕捉，也就是方法返回值是void前端也能知道是否添加成功
                throw new CrmException(CrmEnum.ACTIVITY_UPDATE);
            }
            resultVo.setMessage("修改市场活动成功");
        }
        resultVo.setIsOk(true);
        return resultVo;
    }

    @Override
    public Activity queryById(String id) {

        Activity activity = activityMapper.selectByPrimaryKey(id);
        return activity;
    }

    @Override
    public void deleteActivity(String ids) {
        //前端传过来的ides是以“，”分割的，所以要以，拆分
        String[] idss = ids.split(",");

        //吧String数组转换成List，andIn（）里面，第一个参数是属性名，第二个参数是List数组
        //sql 语句是  ... where id（属性名） in ..(某个范围)
        List<String> id = Arrays.asList(idss);

        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("id",id);

       int count = activityMapper.deleteByExample(example);
       if (count==0){
           throw new CrmException(CrmEnum.ACTIVITY_DELETE);
       }
    }

    @Override
    public Activity toDetail(String id) {
        Activity activity = queryById(id);

        //查询备注信息
        //先根据市场活动主键查询出多个备注，返回一个备注集合
        //然后把这个集合设置到市场活动里面
        ActivityRemark activityRemark = new ActivityRemark();
        //把市场活动的id设置到activityRemark表里面
        activityRemark.setActivityId(id);
        List<ActivityRemark> activityRemarks = activityRemarkMapper.select(activityRemark);
        //然后把这个集合设置到市场活动里面
        activity.setActivityRemarks(activityRemarks);
        return activity;
    }

    @Override
    public List<ActivityRemark> queryActivityRemarks(String id) {

        //查询备注信息
        //先根据市场活动主键查询出多个备注，返回一个备注集合
        //然后把这个集合设置到市场活动里面
        ActivityRemark activityRemark = new ActivityRemark();
        //把市场活动的id设置到activityRemark表里面
        activityRemark.setActivityId(id);
        List<ActivityRemark> activityRemarks = activityRemarkMapper.select(activityRemark);
        //然后把这个集合设置到市场活动里面


        //根据备注查询头像信息
        //1、根据备注查新市场活动信息
        //2、根据市场活动主键查询用户表对应的头像信息
        //3、查询出来的头像设置到备注的img属性中
        for (ActivityRemark remark : activityRemarks) {
            Activity activity = activityMapper.selectByPrimaryKey(remark.getActivityId());
            User user = userMapper.selectByPrimaryKey(activity.getOwner());
            remark.setImg(user.getImg());
        }


        return activityRemarks;
    }

    @Override
    public List<PieVo> activityEcharts() {
        List<PieVo> pieVos = activityMapper.activityEcharts();
        return pieVos;
    }
}