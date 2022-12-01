package com.junius;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author junius
 * @date 2022/11/03 19:46
 **/
public class ThreadLocalDemo {
    @Test
    public void test1(){
        ThreadLocal<Integer> tl = new ThreadLocal<>();
        tl.set(1);
        Integer integer = tl.get();
        System.out.println(integer);
    }
}
