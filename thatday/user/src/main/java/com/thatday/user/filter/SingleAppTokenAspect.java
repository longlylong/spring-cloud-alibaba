package com.thatday.user.filter;

import com.thatday.common.constant.StatusCode;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.RequestPostVo;
import com.thatday.common.model.Result;
import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Order(1)
//@Component
@Log4j2

//运行路径AuthorFilter >> Controller@Valid >> SingleAppTokenAspect >> Controller MethodBody
//不使用网关,单体应用需要打开上面的注解,帮助注入用户信息的
//需要注释掉RequestPostVo RequestGetVo 上面的@NotNull(message = "网关授权失败!")
//pom的 服务发现,服务负载
//AuthorSkipProvider 中添加不需要Token的URL放行,
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
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();

            log.info("");
            log.info("------------------------------------------------");
            log.info(uri);

            Object vo = null;
            if (args.length > 0) {
                vo = args[0];
                log.info(vo);
            }

            if (!AuthorSkipProvider.isSkip(uri)) {
                if (vo != null) {

                    if (vo instanceof RequestPostVo) {
                        String token = request.getHeader(TokenConstant.TOKEN);
                        UserInfo userInfo = TokenUtil.getUserInfo(token);

                        log.info(userInfo);

                        ((RequestPostVo) vo).setUserInfo(userInfo);
                    } else {
                        tokenInvalid();
                    }
                } else {
                    tokenInvalid();
                }
            }
        }
        return point.proceed(args);
    }

    private void tokenInvalid() {
        throw GlobalException.create(Result.buildTokenError());
    }

}
