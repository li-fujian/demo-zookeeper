package com.lifujian.www.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigUtils {

    private static Map<String, String> map = null;
    
    static {
        map = ConfigLoadUtils.loadConfig("config.properties");
    }
    

    
    public static String getStringConfig(String key) {
        return map.get(key);
    }

    public static void setStringConfig(String key, String value) {
        map.put(key, value);
    }
    
    public static List<String> getStringConfigs(String key) {
        String val = map.get(key);
        List<String> list = new ArrayList<String>();
        for(String v :(val == null ? new String[0] : val.split(","))) {
            list.add(v.trim());
        }
        return list;
    }
    
    public static int getIntConfig(String key) {
        String stringVal = map.get(key);
        return Integer.parseInt(stringVal);
    }
    
    public static int getIntConfig(String key, int defaultValue) {
        String stringVal = map.get(key);
        if (stringVal != null) {
            try {
                return Integer.parseInt(stringVal);
            } catch (Exception e) {}
        }
        return defaultValue;
    }

    public static boolean getBoolConfig(String key, boolean defaultValue){
        String stringVal = map.get(key);
        if (stringVal != null) {
            try {
                return Boolean.parseBoolean(stringVal);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }
    
}
