package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tbl_activity_remark")
@NameStyle(Style.normal)
public class ActivityRemark {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String noteContent;
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String editFlag;
    private String activityId;

    @Transient   //排除这个字段，即数据库可以没有这个字段与之对应
    private String img;

    //一个备注对应一个市场活动(根据需求看是否需要写)
   // private Activity activity;

}
