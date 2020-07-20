package com.thatday.user.filter;

import com.thatday.common.model.RequestPostVo;
import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Order(1)
//@Component

//不使用网关,单体应用需要打开这个注解,帮助注入用户信息的
//需要注释掉RequestPostVo RequestGetVo 上面的@NotNull(message = "网关授权失败!")
//pom的 服务发现 服务负载
//然后把报错的不存在的注解或导入清理即可变成单体应用
public class SingleAppTokenAspect {

    @Pointcut("execution(* com.thatday.user.modules.*.backend.*.*(..))" +
            "|| execution(* com.thatday.user.modules.*.controller.*.*(..))")
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
