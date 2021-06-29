package com.bjpowernode.crm.base.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NameStyle(Style.normal)
@Table(name = "tbl_role_permission")
public class RolePermission {
    @Id
    private String id;
    private String roleId;
    private String pId;

}
