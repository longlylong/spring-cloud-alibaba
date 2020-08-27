package com.mm.admin.common.aspect;

import com.thatday.common.model.RequestPostVo;
import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Order(1)
@Component
public class SingleAppTokenAspect {

    @Pointcut("execution(* com.mm.admin.modules.*.controller.*.*(..))")
    public void cutPoint() {
    }

    @Around("cutPoint()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args.length > 0) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            Object o = args[0];

            if (o instanceof RequestPostVo && attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String a = request.getHeader(TokenConstant.TOKEN);
                UserInfo userInfo = TokenUtil.getUserInfo(a);
                ((RequestPostVo) o).setUserInfo(userInfo);
            }
        }
        //用改变后的参数执行目标方法
        return point.proceed(args);
    }

}
