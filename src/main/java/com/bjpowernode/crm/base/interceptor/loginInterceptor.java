package com.bjpowernode.crm.base.interceptor;

import com.bjpowernode.crm.settings.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果session里面有用户就不拦截
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            return true;
        }else {
            //用户没登录，重定向到登录界面
            response.sendRedirect("/crm/login.jsp");
        }
        return false;
    }
}
