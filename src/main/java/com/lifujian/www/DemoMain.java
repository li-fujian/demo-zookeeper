package com.lifujian.www;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifujian.www.register.ServerKeepAlived;
import com.lifujian.www.util.KeepAlive;

/**
 * @author lifujian  2018年1月20日
 * @Description: 启动类
 */
public class DemoMain {
    
    private final static Logger logger = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        
        logger.info("DemoMain start...");
        
        ServerKeepAlived.startup();
        
        // 保证除了zookeeper外，还有其他线程。（本项目将 zookeeper 设为了守护线程，所以你懂的。）
        KeepAlive.keepAilve();
    }
}
