package com.bjpowernode.crm.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;

//定义一个统一返回视图的方法，rest fo风格，花括号里面是能接收到的参数，注解里面是以数组的形式存储数据{1,2,3，..}
@Controller
public class ViewController {

    @RequestMapping({"/toView/{firstView}/{secondView}/{thirdView}", "/toView/{firstView}/{secondView}", "/toView/{firstView}/{secondView}/{thirdView}/{forthView}"})
    public String toView(
            @PathVariable(value = "firstView", required = false) String fistView,
            @PathVariable(value = "secondView", required = false) String secondView,
            @PathVariable(value = "thirdView", required = false) String thirdView,
            @PathVariable(value = "forthView", required = false) String forthView, HttpServletRequest request, Model model) {
//上面这行代码是为了  从请求域（request）中取得这个值
        //得到请求域中的所有name值(通过这个name值可以调用request.getParamita（）这个方法得到传递的参数值)，返回的是一个枚举类型
        Enumeration names = request.getParameterNames();

        //遍历这个枚举类型，取到name值
        while (names.hasMoreElements()) {
            //这里取得的name值就是String类型，所以要强转一下
            //  Object o = names.nextElement();
            String name = (String) names.nextElement();
            String value = request.getParameter(name);
            //然后把value放到model里面
            model.addAttribute(name, value);
        }

        if (thirdView == null) {
            return fistView + File.separator + secondView;
        } else if (forthView == null) {
            return fistView + File.separator + secondView + File.separator + thirdView;
        }else {
            return fistView + File.separator + secondView + File.separator + thirdView + File.separator + forthView;
        }
    }

    //跳转到没有权限的页面
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){

        return "forbidden";
    }
}
