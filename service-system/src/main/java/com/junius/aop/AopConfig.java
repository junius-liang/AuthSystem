package com.junius.aop;

import com.junius.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author junius
 * @date 2022/11/25 13:01
 **/
@Aspect
@Component
@Slf4j
public class AopConfig {
    @Pointcut("@annotation(com.junius.annotation.SysLog)")
    public void pointcut() {

    }

    @Around("com.junius.aop.AopConfig.pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        SysLog annotation = method.getAnnotation(SysLog.class);
        log.info(method.getName()+"执行开始");
        //调用原有的方法
        Object proceed = pjp.proceed();
        System.out.println(annotation.description());
        log.info(method.getName()+"执行结束");
        return proceed;
    }
}
