package com.jadenyee.interceptor;

import com.jadenyee.annotations.Log;
import com.jadenyee.pojo.UserOperationLog;
import com.jadenyee.service.LogService;
import com.jadenyee.utils.WebUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SpringMVC 拦截器 用于记录 用户行为日志信息
 */
public class UserOperationInterceptor implements HandlerInterceptor {
    //日志记录相关的Service
    @Reference
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Log log = handlerMethod.getMethod().getAnnotation(Log.class);
        if (Objects.nonNull(log)){
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            String ipAddr = WebUtils.getIpAddr(request);
            String methodName = handlerMethod.getMethod().getName();
            UserOperationLog uoLog = new UserOperationLog();
            uoLog.setIpAddress(ipAddr);
            uoLog.setUsername(username);
            uoLog.setOperation(log.title());
            uoLog.setOperationTime(LocalDateTime.now());
            uoLog.setMethodName(methodName);
            if (ex != null) {
                uoLog.setExecutionResult(false);
                uoLog.setExceptionInfo(ex.getMessage());
            }else{
                uoLog.setExecutionResult(true);
            }
            try{
                logService.log(uoLog);
            }catch (Exception e){
                e.printStackTrace();
                System.err.println("用户行为日志信息记录失败!");
            }
        }else {
            return;
        }
    }
}
