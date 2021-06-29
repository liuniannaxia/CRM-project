package com.bjpowernode.crm.base.controller;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.settings.workbench.service.ActivityService;
import com.bjpowernode.crm.settings.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BarController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ActivityService activityService;
    @RequestMapping("/bar/transactionEcharts")
    public BarVo transactionEcharts(){
        BarVo barVo = transactionService.transactionEcharts();
        return barVo;
    }
    @RequestMapping("/bar/activityEcharts")
    public List<PieVo> activityEcharts(){
       List<PieVo> pieVos = activityService.activityEcharts();
        return pieVos;
    }

}
