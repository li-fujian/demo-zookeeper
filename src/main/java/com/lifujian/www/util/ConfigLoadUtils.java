package com.lifujian.www.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lifujian  2018年1月20日
 * @Description: 
 */
public class ConfigLoadUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoadUtils.class);

    public static Map<String,String> loadConfig(String configName) {
        Map<String,String> configMap = new HashMap<String,String>();
        Properties prop = new Properties();
        InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream(configName);
        try {
            prop.load(is);
            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                configMap.put(key, value);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return configMap;
    }
}
