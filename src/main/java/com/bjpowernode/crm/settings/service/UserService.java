package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.settings.bean.User;

import java.util.List;

public interface UserService {
   User login(User user);

    String verifyPrincipal(String principal);

    List<Permission> showMenus(User user);

    List<Permission> getCodeByPrincipal(String principal);
}
