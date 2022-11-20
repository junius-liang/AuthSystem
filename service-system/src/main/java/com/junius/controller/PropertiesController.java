package com.junius.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author junius
 * @date 2022/11/18 17:34
 **/
@RestController
public class PropertiesController {
    @GetMapping("/test1")
    public Map<String,Object> getProperties() throws IOException {
        Properties properties = new Properties();
        Map<String, Object> res = new HashMap<>(3);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");
        properties.load(stream);
        Set<String> keys = properties.stringPropertyNames();
        keys.forEach(s -> {
            res.put(s,properties.get(s));
        });
        return res;
    }
}
