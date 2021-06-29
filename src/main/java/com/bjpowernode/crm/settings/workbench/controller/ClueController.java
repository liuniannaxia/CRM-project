package com.bjpowernode.crm.settings.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import com.bjpowernode.crm.settings.workbench.domain.Clue;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;
import com.bjpowernode.crm.settings.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//让下面所有的controller返回json数据，但是不能跳转到其他视图页面了
public class ClueController {
    @Autowired
    private ClueService clueService;


    @RequestMapping("/workbench/clue/creatClue")
    //因为有创建者，所有要从session里面取出当前的用户就是创建者(从session里面取出user对象，然后得到创建者属性设置到Clue中)，前端传过来的数据要用Clue来接
    public ResultVo creatClue( Clue clue,HttpSession session){
        User user = (User) session.getAttribute("user");
        //正常情况下，以下代码放到业务层去设置
        ResultVo resultVo = new ResultVo();
        clue.setCreateBy(user.getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setId(UUIDUtil.getUUID());

        try{
             clueService.createClue(clue);
            resultVo.setIsOk(true);
            resultVo.setMessage("添加线索成功");

        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }


        return resultVo;
    }

    //查询线索
    @RequestMapping("/workbench/clue/queryClue")
    public List<Clue> queryClue(Clue clue){
        List<Clue> clues = clueService.createClues(clue);
        return clues;
    }

    //查询线索对应的市场活动
    @RequestMapping("/workbench/clue/queryClueActivity")
    public List<Activity> queryClueActivity(String id){
        List<Activity> activitys = clueService.queryClueActivity(id);
        return activitys;
    }

    //查询关联窗口中的市场活动
    @RequestMapping("/workbench/queryAllActivitys")
    public List<Activity> queryAllActivitys(String name,String id){
       List<Activity> activities = clueService.queryAllActivitys(name,id);
        return activities;
    }

    //线索关联市场活动
    @RequestMapping("/workbench/clue/relateActivity")
    public ResultVo relateActivitys(String ids,String id){
        ResultVo resultVo = new ResultVo();
        try{
            clueService.insertClueActivity(ids,id);
            resultVo.setIsOk(true);
            resultVo.setMessage("关联市场活动成功");
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
//解除关联市场活动
    @RequestMapping("/workbench/clue/deleteActivity")
    public ResultVo relateActivity(String id,String activityId){
        ResultVo resultVo = new ResultVo();
        try{
            clueService.deleteClueActivity(id,activityId);
            resultVo.setIsOk(true);
            resultVo.setMessage("解除绑定市场活动成功");
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //根据线索id查询转换页面的数据
    @RequestMapping("/workbench/clue/queryById")
    public Clue queryById(String id){
       Clue clue = clueService.queryById(id);
        return clue;
    }

    //线索转换
    @RequestMapping("/workbench/clue/convert")
    public ResultVo convert(String clueId, String isCreateTransaction, Transaction transaction,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try{
            User user = (User) session.getAttribute("user");
            transaction.setCreateBy(user.getName());
            clueService.convert(clueId,isCreateTransaction,transaction);
            resultVo.setIsOk(true);
            resultVo.setMessage("线索转换成功");
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //查询线索交易模块中，该线索对应的市场活动
    @RequestMapping("/workbench/clue/queryRelationActivity")
    public List<Activity> queryRelationActivity(String id,String name){
        List<Activity> activities = clueService.queryRelationActivity(id,name);
        return activities;
    }

}
