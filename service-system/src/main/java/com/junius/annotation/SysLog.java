package com.junius.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author junius
 * @date 2022/11/25 12:59
 **/
@Target({ElementType.METHOD, ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  SysLog {
    String description() default "";
}
