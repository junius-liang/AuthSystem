package com.junius.service.impl;

import com.junius.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @author junius
 * @date 2022/11/28 12:36
 **/
@Service
public class SysLogServiceImpl2 implements SysLogService {
    @Override
    public void addLog() {
        System.out.println(this.getClass().getName()+"---------------------SysLogServiceImpl2");
    }
}
