package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_contacts_remark")
@NameStyle(Style.normal)
public class ContactsRemark {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;
    private String noteContent;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String editFlag;
    private String contactsId;

}
