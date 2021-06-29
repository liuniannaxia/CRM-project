package com.bjpowernode.crm.settings.workbench.mapper;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.settings.workbench.domain.Transaction;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransactionMapper extends Mapper<Transaction> {
    List<BarVo> transactionEcharts();
}
