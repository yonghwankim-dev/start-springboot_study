package org.zerock.interceptor;

import lombok.extern.java.Log;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        String dest = request.getParameter("dest");
        if(dest != null){
            request.getSession().setAttribute("dest", dest);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
