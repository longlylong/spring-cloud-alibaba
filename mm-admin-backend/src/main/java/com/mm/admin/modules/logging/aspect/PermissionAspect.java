package com.mm.admin.modules.logging.aspect;

import com.mm.admin.common.utils.ValidationUtil;
import com.mm.admin.modules.system.service.UserService;
import com.mm.admin.modules.system.service.dto.UserDto;
import com.thatday.common.token.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class PermissionAspect {

    @Autowired
    UserService userService;

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
    @Before("logPointcut()")
    public void Before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            Object o = args[0];

            if (attributes != null) {
                UserInfo userInfo = ValidationUtil.getUserInfo(attributes.getRequest());
                UserDto userDto = userService.findById(userInfo.getUserId());
            }
        }
    }
}
