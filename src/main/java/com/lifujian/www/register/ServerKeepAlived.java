package com.lifujian.www.register;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ServerKeepAlived {
    
    private static ScheduledThreadPoolExecutor executor;
    
    // 这里的host和port的指的是 zkCli(即 demo-zookeeper 本身)，它可能部署在多台机器上， 多点部署的的时候，以此区分不同点。
    private static String host = getHost();
    private static int port = getPort();
    
    static {
        executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            
            ThreadFactory wrappedFactory = Executors.defaultThreadFactory();
            
            public Thread newThread(Runnable r) {
                Thread thread = this.wrappedFactory.newThread(r);
                thread.setName("KeepAlivedWorker");
                thread.setDaemon(true);  // 设为守护线程。说到底，zk也是为主业务服务的。
                return thread;
            }
        });
        
    }

    public static void startup() {
        executor.execute(new Runnable() {
            
            public void run() {
                ZkClient zkCli = new ZkClient(host, port);
                try {
                    zkCli.startup();
                } catch (IOException e) {
                    e.printStackTrace();
                    zkCli.shutdown();
                }
            }
        });
    }
    
    public static String getHost() {
        int i = (int)(Math.random() * 10);
        return "10.0.0." + i;
    }
    
    public static int getPort() {
        int i = (int)(Math.random() * 10);
        return 10000 + i;
    }

}
