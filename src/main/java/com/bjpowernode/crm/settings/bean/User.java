package com.bjpowernode.crm.settings.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data   //不用写setter or getter方法
@Table(name = "tbl_user")       //保证数据库名字和实体类名字一致
@NameStyle(Style.normal)
public class User {
    @Id
    //设置主键递增
    @KeySql(useGeneratedKeys = true)
    private String id;
    //登录账户
    private String loginAct;
    //昵称
    private String name;
    //登录密码，在数据库中存储也要加密，一般用sha，md5来加密
    //但是简单的md5容易被破解，所以要加一些干扰的符号--“盐”
    //一般用难得糊涂——“Hutool”工具类，在pom文件中导入依赖
    private String loginPwd;
    private String email;
    //失效时间
    private String expireTime;
    //锁定状态（0表示锁定，1表示启用）
    private String lockState;
    //部门编号
    private String deptno;
    //允许登录的ip
    private String allowIps;

    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String img;

}
