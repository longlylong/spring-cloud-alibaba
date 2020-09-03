package com.mm.admin.common.aspect;

import com.mm.admin.modules.system.domain.vo.RoleVo;
import com.mm.admin.modules.system.domain.vo.UserVo;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String uri = request.getRequestURI();
            if (!AuthorSkipProvider.isSkip(uri)) {
                String token = request.getHeader(TokenConstant.TOKEN);
                TokenUtil.checkTokenAndThrowException(token);

                Object[] args = point.getArgs();
                if (args.length > 0) {
                    Object o = args[0];
                    UserInfo userInfo = TokenUtil.getUserInfo(token);
                    if (o instanceof RequestPostVo) {
                        ((RequestPostVo) o).setUserInfo(userInfo);

                    } else if (o instanceof RoleVo) {
                        ((RoleVo) o).setUserInfo(userInfo);

                    } else if (o instanceof UserVo) {
                        ((UserVo) o).setUserInfo(userInfo);
                    }
                }
            }
        }
        //用改变后的参数执行目标方法
        return point.proceed(point.getArgs());
    }

}
