package com.zyy.aspect;

import com.zyy.dao.LogMapper;
import com.zyy.model.Log;
import com.zyy.util.Util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class AspectLog {

    @Autowired
    LogMapper logMapper;

    private Logger logger = LoggerFactory.getLogger(AspectLog.class);

    @Pointcut("execution(public * com.zyy.controller.*.*(..))"+"&& !execution(public * com.zyy.controller.EquipDataController.show(..))")
    public void webLog(){}

    /**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yy-MM-dd HH:mm:ss");
        String str = sdf.format(new Date());
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("TIME : " + str);
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        Log log = new Log();
        log.setId(Util.getUUID());
        log.setArgs(Arrays.toString(joinPoint.getArgs()));
        log.setClassmethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.setHttpmethod(request.getMethod());
        log.setIp(request.getRemoteAddr());
        log.setTime(str);
        log.setUrl(request.getRequestURL().toString());

        logMapper.insert(log);
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

}
