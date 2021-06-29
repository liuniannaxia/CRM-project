package com.bjpowernode.crm.settings.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import com.bjpowernode.crm.settings.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.settings.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableServer.POAHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/Activity/list")
    @ResponseBody
    @RequiresPermissions("activity:list")

    //即使前端没有给后台传值，默认值也为1,下面这个注解可以更改默认值，不写也可以，默认是1
    public PageInfo<Activity> list(@RequestParam(defaultValue = "1") int page, int pageSize, Activity activity) {
        PageHelper.startPage(page, pageSize);
        List<Activity> activities = activityService.list(activity);
        //作用是生成下一页，第几页..
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return pageInfo;
    }


    //查询添加按钮中的下拉列表框
    @RequestMapping("/workbench/activity/queryAllUsers")
    @ResponseBody
    public List<User> queryAllUsers() {
        return activityService.queryAllUsers();
    }


    @RequestMapping("/workbench/activity/creatOrUpdate")
    @ResponseBody
    @RequiresPermissions("activity:add1")
    //用一个Activity对象来接前端传过来用户需要插入的数据
    public ResultVo creatOrUpDate(Activity activity, HttpSession session) {
        //局部变量要给一个初始值                                 
        ResultVo resultVo = null;

        User user = (User) session.getAttribute("user");

        try {
            resultVo = activityService.creatOrUpDate(activity, user.getName());

        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }

    @RequestMapping("/workbench/editActivity/queryById")
    @ResponseBody
    public Activity queryById(String id) {
        Activity activity = activityService.queryById(id);
        return activity;
    }


    //删除市场活动的方法
    @RequestMapping("/workbench/activity/deleteActivity")
    @ResponseBody
    @RequiresPermissions("activity:delete1")
    public ResultVo deleteActivity(String ids) {
        ResultVo resultVo = new ResultVo();
        try {
            activityService.deleteActivity(ids);
            resultVo.setIsOk(true);
            resultVo.setMessage("删除市场活动成功");
        } catch (CrmException e) {
            e.getMessage();
        }
        return resultVo;
    }

    //跳转到详情页
    @RequestMapping("/workbench/activity/toDetail")
    @ResponseBody
    public String toDetail(String id, Model model) {
        Activity activity = activityService.toDetail(id);
        //吧activity放到model里面
        model.addAttribute("activity", activity);
        return "forward:/workbench/activity/detail";
    }

    //跳转到详情页        方式 2
    @RequestMapping("/workbench/activity/toDetail02")
    public String toDetail02(String id, Model model) {
        Activity activity = activityService.queryById(id);
        //吧activity放到model里面
        model.addAttribute("activity", activity);
        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/selectActivityRemarks")
    @ResponseBody
    public List<ActivityRemark> selectActivityRemarks(String id) {
        List<ActivityRemark> activityRemarks = activityService.queryActivityRemarks(id);
        //吧activity放到model里面

        return activityRemarks;
    }
}

