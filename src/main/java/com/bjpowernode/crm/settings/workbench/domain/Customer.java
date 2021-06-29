package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_customer")
@Data
@NameStyle(Style.normal)
public class Customer {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String owner;
    private String name;
    private String website;
    private String phone;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String contactSummary;
    private String nextContactTime;
    private String description;
    private String address;

}
