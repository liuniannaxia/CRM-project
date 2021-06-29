package com.bjpowernode.crm.settings.service.userservice.impl;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.base.bean.RolePermission;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.TreeMenu;
import com.bjpowernode.crm.settings.mapper.RoleMapper;
import com.bjpowernode.crm.settings.mapper.RolePermissionMapper;
import com.bjpowernode.crm.settings.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public List<TreeMenu> queryPermission(String id) {
        //查询所有的权限
        List<Permission> permissions = roleMapper.queryPermission();
        //查询角色对应的权限
        List<Permission> rolePermissions = roleMapper.queryRolePermission(id);
        List<TreeMenu> treeMenus = new ArrayList<>();
        for (Permission permission : permissions) {
            TreeMenu treeMenu = new TreeMenu();
            treeMenu.setId(permission.getId());
            treeMenu.setName(permission.getPname());
            if (permission.getParent_id()!=null){
                treeMenu.setPId(permission.getParent_id());
            }
            //设置一级菜单
            if ("0".equals(permission.getParent_id())){
                treeMenu.setOpen(true);
            }

            for (Permission p:rolePermissions){
                if (p.getId().equals(permission.getId())){
                    treeMenu.setChecked(true);
                }
            }
            treeMenus.add(treeMenu);
        }

        return treeMenus;
    }

    @Override
    public void authrize(String ids, String id) {
        //1.把用户的权限删除掉
            RolePermission rp = new RolePermission();
            rp.setRoleId(id);
            rolePermissionMapper.delete(rp);
        //2.把当前角色已经赋予的权限插入表中
        String[] pIds = ids.split(",");
        for (String pId : pIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(UUIDUtil.getUUID());
            rolePermission.setPId(pId);
            rolePermission.setRoleId(id);
            rolePermissionMapper.insertSelective(rolePermission);
        }



    }
}
