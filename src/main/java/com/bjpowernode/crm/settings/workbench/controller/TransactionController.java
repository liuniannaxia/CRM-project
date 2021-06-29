package com.bjpowernode.crm.settings.workbench.controller;

import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Stage;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;
import com.bjpowernode.crm.settings.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/workbench/transaction/queryCustomerName")
    @ResponseBody
    public List<String> queryCustomerName(String customerName) {
        List<String> queryCustomerName = transactionService.queryCustomerName(customerName);

        return queryCustomerName;
    }


    //查询阶段对应的可能性
    @RequestMapping("/workbench/transaction/queryPossibilityByStage")
    @ResponseBody
    public String queryPossibilityByStage(String stage, HttpSession session) {
        //不需要调用业务层的方法，因为数据存在servletContext里面
        Map<String, String> map = (Map<String, String>) session.getServletContext().getAttribute("Stage2Possibility");

        return map.get(stage);
    }
    @RequestMapping("/workbench/transaction/queryDetail")
    @ResponseBody
    public Transaction queryDetail(String id) {
       Transaction transaction = transactionService.queryDetail(id);
    return transaction;
    }


    //查询阶段图标，点击阶段图标改变当前阶段，查询阶段历史记录
    @RequestMapping("/workbench/transaction/stageList")
    @ResponseBody
    public Map<String,Object> stageList(String id,HttpSession session,Integer index) {
        User user = (User) session.getAttribute("user");
        Map<String,String> stage2Possibility = (Map<String, String>) session.getServletContext().getAttribute("Stage2Possibility");
        Map<String,Object> map = transactionService.stageList(id,stage2Possibility,index,user);
        return map;
    }

}
