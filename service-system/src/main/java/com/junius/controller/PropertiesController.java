package com.junius.controller;

import com.junius.service.SysPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author junius
 * @date 2022/11/18 17:34
 **/
@RestController
public class PropertiesController {
    @Autowired
    private SysPropertiesService sysPropertiesService;

    @GetMapping("/test1")
    public Map<String,Object> getProperties() throws IOException {
        return sysPropertiesService.getProperties();
    }
}
