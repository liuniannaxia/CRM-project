package com.bjpowernode.crm.base.bean;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class Permission {
    @Id
    private String id;
    private String pname;
    private String code;
    private String url;
    private String create_time;
    private String parent_id;//父菜单
    //当前菜单的子菜单
    private List<Permission> permissions;
}
