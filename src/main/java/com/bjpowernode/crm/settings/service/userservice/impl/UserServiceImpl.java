package com.bjpowernode.crm.settings.service.userservice.impl;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.base.exception.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        //这里下面比较比较的是address（即不是null的值）
        String address = user.getAllowIps();
        //以下两行代码参与查询了，所以设置为null值
        user.setAllowIps(null);
        user.setLoginPwd(null);
        //把用户输入的明文密码加密
        //一个账号只能对应一个用户，所以，凭证可以不用在获取去判断
        //user.setLoginPwd(SecureUtil.md5(user.getLoginPwd()));
        //注意，下面把user重新赋值了，就是从数据库里面重新查出user的最新值
         user = userMapper.selectOne(user);
        if (user != null) {
            //用户存在
            if ( DateTimeUtil.getSysTime().compareTo(user.getExpireTime())>0){
                throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT_EXPIRED);
            }else if ("0".equals(user.getLockState())){
                throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT_LockState);
            }
            if (!user.getAllowIps().contains(address)){
                throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT_IP);
            }

        } else {
            //用户名或者密码错误，使用自定义异常，反馈信息
            throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT_PASSWORD);
        }
        return user;
    }

    @Override
    public String verifyPrincipal(String principal) {
        Example example = new Example(User.class);

        example.createCriteria().andEqualTo("loginAct",principal);
        List<User> users = userMapper.selectByExample(example);
        if (users.size()!=0){
            User user = users.get(0);
            return user.getLoginPwd();
        }
        throw new UnknownAccountException();
    }

    @Override
    public List<Permission> showMenus(User user) {
       List<Permission> permissions = userMapper.showMenus(user.getId());
       //查询所有的菜单权限
        List<Permission> allPermissions = userMapper.showAll(user.getId());
        //查询的主菜单（一级菜单）
        for (Permission permission : permissions){
            List<Permission> sons = new ArrayList<>();
            //查询的所有菜单
            for (Permission allPermission : allPermissions) {
                //肯定是某个根菜单的二级子菜单
                if (allPermission.getParent_id()!=null&&!"0".equals(allPermission.getParent_id())&&allPermission.getCode()==null){
                    //说明是父子关系
                    if (allPermission.getParent_id().equals(permission.getId())){
                        sons.add(permission);
                    }
                }
            }
            permission.setPermissions(sons);
        }

        return permissions;
    }


    //根据身份查询权限
    @Override
    public List<Permission> getCodeByPrincipal(String principal) {
        return userMapper.getCodeByPrincipal(principal);
    }

}