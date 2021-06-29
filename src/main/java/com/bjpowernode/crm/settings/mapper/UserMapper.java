package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.settings.bean.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<Permission> showMenus(String id);

    List<Permission> showAll(String id);

    List<Permission> getCodeByPrincipal(String principal);
}
