package com.junius.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junius
 * @date 2022/10/26 13:05
 * 自定义异常
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private int code;
    private String msg;
}
