package com.bjpowernode.crm.settings.workbench.service;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.workbench.domain.Stage;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<String> queryCustomerName(String customerName);

    Transaction queryDetail(String id);

    Map<String,Object> stageList(String id, Map<String,String> stage2Possibility, Integer index, User user);

    BarVo transactionEcharts();
}
