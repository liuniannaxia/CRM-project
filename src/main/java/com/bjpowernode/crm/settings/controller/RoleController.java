package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.realm.CustomRealm;
import com.bjpowernode.crm.settings.bean.TreeMenu;
import com.bjpowernode.crm.settings.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private CustomRealm customRealm;



    @RequestMapping("/settings/toPermission")
    public String queryPermission(String id, Model model){
        model.addAttribute(id);
        return "settings/qx/user/permission";
    }

    //查询角色对应的所有的权限
    @RequestMapping("/settings/role/queryPermission")
    @ResponseBody
    public List<TreeMenu> queryPermission(String id){
        List<TreeMenu> treeMenus = roleService.queryPermission(id);
        return treeMenus;
    }


    //授权
    @RequestMapping("/settings/role/authrize")
    @ResponseBody
    public ResultVo authrize(String ids,String id){
        ResultVo resultVo = new ResultVo();
        roleService.authrize(ids,id);
        resultVo.setIsOk(true);
        resultVo.setMessage("授权成功");
        customRealm.clearCache();
        return resultVo;
    }
}
