package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.TreeMenu;

import java.util.List;

public interface RoleService {
    List<TreeMenu> queryPermission(String id);

    void authrize(String ids, String id);
}
