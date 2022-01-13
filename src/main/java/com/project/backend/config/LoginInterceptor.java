package com.project.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Interceptor added before controller
@Component
public class LoginInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(" PRE HANDLE INTERCEPTOR ");
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(" POST HANDLE INTERCEPTOR ");
        modelAndView.addObject("loginDisplaySpeed", System.currentTimeMillis()-(Long)request.getAttribute("startTime"));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        Long consumedTime = System.currentTimeMillis()-(Long)request.getAttribute("startTime");
        logger.error("Login display in msec: "+ consumedTime);
        System.out.println(" COMPLETED INTERCEPTOR " + consumedTime);
    }
}
