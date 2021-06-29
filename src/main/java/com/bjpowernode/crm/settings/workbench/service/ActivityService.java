package com.bjpowernode.crm.settings.workbench.service;

import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import com.bjpowernode.crm.settings.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityService {
     List<Activity> list(Activity activity);

    List<User> queryAllUsers();

    ResultVo creatOrUpDate(Activity activity, String name);

    Activity queryById(String id);

    void deleteActivity(String ids);

    Activity toDetail(String id);

    List<ActivityRemark> queryActivityRemarks(String id);

    List<PieVo> activityEcharts();
}
