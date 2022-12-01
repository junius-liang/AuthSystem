package com.junius.annotation;

import com.junius.enums.RedisEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author junius
 * @date 2022/12/01 17:36
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisAnno {
    /**
     * @return Redis键的名称
     */
    String keyName() default "";

    /**
     * @return 过期时间，默认30
     */
    long timeout() default 30;

    /**
     * @return 过期时间单位，默认分钟
     */
    TimeUnit unit() default TimeUnit.MINUTES;

    /**
     * 缓存形式
     */
    RedisEnum cacheType() default RedisEnum.GetAndAdd;
}

