package com.junius;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author junius
 * @date 2022/11/18 17:19
 **/
public class PropertiesTest {
    @Test
    public void test1() throws IOException {
        Properties properties = new Properties();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");
        properties.load(stream);
        Set<String> keys = properties.stringPropertyNames();
        keys.forEach(s -> {
            System.out.println(s+"---"+properties.get(s));
        });
    }
}
