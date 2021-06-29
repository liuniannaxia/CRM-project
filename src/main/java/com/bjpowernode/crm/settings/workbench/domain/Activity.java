package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@NameStyle(Style.normal)
@Table(name = "tbl_activity")
public class Activity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String owner;
    private String name;
    private String startDate;
    private String endDate;
    private String cost;
    private String description;
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;

    //一个市场活动可以对应多个备注（一对多）
    private List<ActivityRemark> activityRemarks;

}
