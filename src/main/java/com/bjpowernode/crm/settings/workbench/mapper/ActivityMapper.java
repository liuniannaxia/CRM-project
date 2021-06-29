package com.bjpowernode.crm.settings.workbench.mapper;

import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.settings.workbench.domain.Activity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ActivityMapper extends Mapper<Activity> {
    List<PieVo> activityEcharts();
}
