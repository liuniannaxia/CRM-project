package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.settings.bean.TreeMenu;
import com.bjpowernode.crm.settings.bean.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<TreeMenu> {

    List<Permission> queryPermission();

    List<Permission> queryRolePermission(String id);
}
