package com.bjpowernode.crm.test;

import cn.hutool.crypto.SecureUtil;
import com.bjpowernode.crm.settings.bean.Mapper.TypeMapper;
import com.bjpowernode.crm.settings.bean.Type;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

public class MyTestTkMapper {
    @Test
    public void test01(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/spring-applicationContext.xml");
        TypeMapper typeMapper = (TypeMapper) beanFactory.getBean("typeMapper");
        Type type = new Type();
        type.setCode("333");
        type.setName("234");
        typeMapper.insert(type);
    }

    @Test
    //UUID自动随机生成不同的数字，可以做主键
    public void test02(){

       String id =  UUID.randomUUID().toString().replace("-","");
        System.out.println(id);
        String admin1 = SecureUtil.md5("admin");
        System.out.println(admin1);
    }

    @Test
    //UUID自动随机生成不同的数字，可以做主键
    public void test03(){
//创建SecurityManager工厂，通过ini配置文件创建 SecurityManager工厂
//        Factory<SecurityManager> factory =
//                new IniSecurityManagerFactory("classpath:shiro-authenticate.ini");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-test/shiro-authenticate.ini");
        //创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
// 设置SecurityManager到运行环境中，保持单例模式
        SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);

//获取subject
        Subject subject = SecurityUtils.getSubject();

//用户名和密码token
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken
                        ("zhangsan","123");
//测试登录
        subject.login(usernamePasswordToken);
//是否登录成功
        boolean isOk = subject.isAuthenticated();
        System.out.println(isOk);
    }


    @Test
    //测试shiro加密
    public void test04(){
        String zhanghao = "wangwu";
        String salt = "123";
       // SimpleHash    是shiro提供的一种加密方法
        SimpleHash simpleHash = new SimpleHash("md5",zhanghao,salt,1);
        System.out.println(simpleHash);
    }
}
