package com.junius.service.impl;

import com.junius.annotation.SysLog;
import com.junius.service.SysPropertiesService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author junius
 * @date 2022/11/25 12:56
 **/
@Service
public class SysPropertiesServiceImpl implements SysPropertiesService {



    @Override
    @SysLog(description = "读取配置信息")
    public Map<String,Object> getProperties() {
        Properties properties = new Properties();
        Map<String, Object> res = new HashMap<>(3);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> keys = properties.stringPropertyNames();
        keys.forEach(s -> {
            res.put(s,properties.get(s));
        });
        return res;
    }
}
