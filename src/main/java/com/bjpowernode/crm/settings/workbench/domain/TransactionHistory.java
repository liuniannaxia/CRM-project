package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_tran_history")
@Data
@NameStyle(Style.normal)
public class TransactionHistory {
    @Id
    private String id;
    private String stage;
    private String money;
    private String expectedDate;
    private String createTime;
    private String createBy;
    private String tranId;
    private String possibility;

}
