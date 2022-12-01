package com.junius.controller;

import com.junius.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author junius
 * @date 2022/11/28 12:37
 **/
@RestController
public class SysLogController {
//    注入方式1
//    @Autowired
//    private List<SysLogService> sysLogList;

//    注入方式二
    @Autowired
    private Map<String,SysLogService> sysLogMap;

    @GetMapping("/log")
    public void getLogs() {
        for (String name : sysLogMap.keySet()) {
            SysLogService sysLogService = sysLogMap.get(name);
            sysLogService.addLog();
        }
    }
}
