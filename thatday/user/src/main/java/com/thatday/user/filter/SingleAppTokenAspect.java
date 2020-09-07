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

//运行路径AuthorFilter >> Controller@Valid >> SingleAppTokenAspect >> Controller MethodBody
//不使用网关,单体应用需要打开上面的注解,帮助注入用户信息的
//需要注释掉RequestPostVo RequestGetVo 上面的@NotNull(message = "网关授权失败!")
//pom的 服务发现,服务负载
//AuthorSkipProvider 中添加不需要Token的URL放行,可参考网关模块的AuthorSkipProvider.isSkip
//最后把报错的不存在的注解或导入清理即可变成单体应用
public class SingleAppTokenAspect {

    @Pointcut("execution(* com.thatday.user.modules.*.backend.*.*(..))" +
            "|| execution(* com.thatday.user.modules.*.controller.*.*(..))")
    public void cutPoint() {
    }

    @Around("cutPoint()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
//            String uri = request.getRequestURI();
//            if (!AuthorSkipProvider.isSkip(uri)) {
            if (args.length > 0) {
                Object o = args[0];
                if (o instanceof RequestPostVo) {
                    HttpServletRequest request = attributes.getRequest();
                    String a = request.getHeader(TokenConstant.TOKEN);
                    UserInfo userInfo = TokenUtil.getUserInfo(a);
                    ((RequestPostVo) o).setUserInfo(userInfo);
                }
            }
//            }
        }

        //用改变后的参数执行目标方法
        return point.proceed(args);
    }

}
