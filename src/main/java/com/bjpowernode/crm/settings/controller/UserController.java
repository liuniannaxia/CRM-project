package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.bean.Permission;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/user/login")
    @ResponseBody
    public ResultVo login(User user, HttpServletRequest request, HttpSession session) {
        //把用户输入的账号和密码放到 token里面，调自定义realm里面的shiro的方法进判断
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getLoginAct(),user.getLoginPwd());
        ResultVo resultVo = new ResultVo();
        //获取用户的登录IP
        String address = request.getRemoteAddr();
        user.setAllowIps(address);

        try {
            Subject subject = SecurityUtils.getSubject();
            //shiro的login方法
            subject.login(usernamePasswordToken);
            //System.out.println("登录成功");
            //登录成功
//把数据库里面的user拿出来设置到session里面，然后根据需求再在前端页面取出来要显示的内容
           user = userService.login(user);
            session.setAttribute("user",user);
           resultVo.setIsOk(true);
           resultVo.setMessage("登录成功");
            //userService.login(user);
        } catch (CrmException e) {
            e.getMessage();
            //登录失败
            resultVo.setIsOk(false);
            resultVo.setMessage(e.getMessage());
        }catch (UnknownAccountException e){
            resultVo.setMessage("账号错误");
        }catch (IncorrectCredentialsException e){
            resultVo.setMessage("密码错误");
        }
        return resultVo;
    }

//    @RequestMapping("/settings/user/toIndex")
//    public String toIndex(){
//        // /crm//WEB-INF/workbench/workbech/index.jap
//        return "index";
//    }
//    @RequestMapping("/toWorkBenchIndex")
//    public String toWorkBenchIndex(){
//        return "main/index";
//
//    }

    //退出系统
    @RequestMapping("settings/user/loginOut")
    public String loginOut(){
        Subject subject  = SecurityUtils.getSubject();
        subject.logout();
        //重定向
        return "redirect:/login.jsp";

    }
    //认证通过后，展示菜单页
    @RequestMapping("/settings/user/toIndex")
    public String showMenus(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<Permission> permissions =userService.showMenus(user);
        model.addAttribute("permissions",permissions);
        return "workbench/index";
    }

}
