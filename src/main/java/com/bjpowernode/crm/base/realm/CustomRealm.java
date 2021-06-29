package com.bjpowernode.crm.base.realm;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.settings.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据身份获取用户
        String  principal = (String) principalCollection.getPrimaryPrincipal();
        //根据身份查询对应的权限（code）
       List<Permission> permissions = userService.getCodeByPrincipal(principal);
       List<String> coads = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission.getCode()!=null){
                coads.add(permission.getCode());
            }

        }
        //因为下面这个集合可以接集合参数，所以把他创建出来
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(coads);
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String principal = (String) authenticationToken.getPrincipal();
        if (principal!=null){
            String credential = userService.verifyPrincipal(principal);
            String salt = "123";
            return new SimpleAuthenticationInfo(principal,credential, ByteSource.Util.bytes(salt),"customRealm");
        }else {
            throw new UnknownAccountException();
        }

    }

    //定义一个清除缓存的函数
    public void clearCache(){
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principalCollection);
    }


}
