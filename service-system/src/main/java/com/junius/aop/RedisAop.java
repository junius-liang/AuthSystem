package com.junius.aop;

import com.junius.annotation.RedisAnno;
import com.junius.enums.RedisEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author junius
 * @date 2022/12/01 17:45
 **/
@Aspect
@Component
@Slf4j
public class RedisAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.junius.annotation.RedisAnno)")
    public void pointcut(){

    }

    /**
     * 通知
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisAnno annotation = method.getAnnotation(RedisAnno.class);
        String keyName = annotation.keyName();
        long timeout = annotation.timeout();
        TimeUnit unit = annotation.unit();
        RedisEnum redisEnum = annotation.cacheType();
        if (redisEnum.equals(RedisEnum.GetAndAdd)){
            Object o = redisTemplate.opsForValue().get(keyName);
            if (o!=null){
                return o;
            }else {
                Object proceed = pjp.proceed();
                redisTemplate.opsForValue().set(keyName,proceed,timeout,unit);
                log.info("Redis数据查询完毕，若缓存无此数据，则自动查询数据并添加到缓存中");
                return proceed;
            }
        }else if(redisEnum.equals(RedisEnum.OnlyAdd)){
            Object proceed = pjp.proceed();
            redisTemplate.opsForValue().set(keyName,proceed,timeout,unit);
            return proceed;
        }else {
            redisTemplate.delete(keyName);
            return pjp.proceed();
        }
    }

}
