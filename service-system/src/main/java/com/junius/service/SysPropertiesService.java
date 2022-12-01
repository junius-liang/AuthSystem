package com.junius.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author junius
 * @date 2022/11/25 12:55
 **/
public interface SysPropertiesService
{
    public Map<String,Object> getProperties();
}
