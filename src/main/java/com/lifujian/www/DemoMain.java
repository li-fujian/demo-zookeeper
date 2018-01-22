package com.lifujian.www;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifujian.www.register.ServerKeepAlived;

/**
 * @author lifujian  2018年1月20日
 * @Description: 启动类
 */
public class DemoMain {
    
    private final static Logger logger = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        
        logger.info("DemoMain start...");
        
        ServerKeepAlived.startup();
    }
}
