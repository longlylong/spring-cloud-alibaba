package com.mm.admin.modules.security.config;

import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.common.utils.StringUtils;
import com.mm.admin.common.utils.ValidationUtil;
import com.mm.admin.modules.system.domain.Menu;
import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.service.UserService;
import com.thatday.common.constant.UserCode;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.token.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Pointcut("@annotation(com.mm.admin.common.annotation.UserPermission)")
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

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method signatureMethod = signature.getMethod();
            UserPermission userPermission = signatureMethod.getAnnotation(UserPermission.class);
            String permission = userPermission.value();

            if (StringUtils.isNotEmpty(permission)) {
                String[] permissions = permission.split(",");

                if (attributes != null) {
                    UserInfo userInfo = ValidationUtil.getUserInfo(attributes.getRequest());
                    User user = userService.getOne(userInfo.getUserId());
                    if (user != null) {
//                        List<String> elPermissions = user.getRoles().stream().flatMap(role -> role.getMenus().stream())
//                                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
//                                .map(Menu::getPermission).collect(Collectors.toList());
//
//                        if (Arrays.stream(permissions).noneMatch(elPermissions::contains)) {
//                            throw GlobalException.create(UserCode.UserNotAuthorCode, UserCode.UserNotAuthorMsg);
//                        }
                    } else {
                        throw GlobalException.create(UserCode.UserNotAuthorCode, UserCode.UserNotAuthorMsg);
                    }
                }
            }
        }
    }
}
