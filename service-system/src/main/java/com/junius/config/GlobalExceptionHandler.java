package com.junius.config;

import com.junius.Result;
import com.junius.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author junius
 * @date 2022/10/26 12:58
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        return Result.fail().message("系统错误,请稍后尝试");
    }

    /**
     * 非法参数异常
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result IllegalArgumentException() {
        return Result.fail().message("非法参数,,请稍后尝试");
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result myError(MyException e) {
        return Result.fail().message("操作错误: "+e);
    }

}
