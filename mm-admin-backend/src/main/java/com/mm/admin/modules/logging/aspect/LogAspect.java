package com.mm.admin.modules.logging.aspect;

import com.mm.admin.common.utils.RequestHolder;
import com.mm.admin.common.utils.StringUtils;
import com.mm.admin.common.utils.ThrowableUtil;
import com.mm.admin.modules.logging.domain.Log;
import com.mm.admin.modules.logging.service.LogService;
import com.thatday.common.model.RequestPostVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.mm.admin.modules.logging.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(joinPoint), StringUtils.getBrowser(request), StringUtils.getIp(request), joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(joinPoint), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    public String getUsername(JoinPoint joinPoint) {
        try {
            Object o = joinPoint.getArgs()[0];
            if (o instanceof RequestPostVo) {
                return ((RequestPostVo) o).getUserInfo().getUserId() + "";
            }
            return "-1";
        } catch (Exception e) {
            return "-1";
        }
    }
}
