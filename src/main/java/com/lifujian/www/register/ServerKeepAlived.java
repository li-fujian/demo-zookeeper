package com.lifujian.www.register;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ServerKeepAlived {
    
    private static ScheduledThreadPoolExecutor executor;
    
    private static String host = null;
    
    private static int port = -1;
    
    static {
        executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            
            ThreadFactory wrappedFactory = Executors.defaultThreadFactory();
            
            public Thread newThread(Runnable r) {
                Thread thread = this.wrappedFactory.newThread(r);
                thread.setName("KeepAlivedWorker");
                thread.setDaemon(true);
                return thread;
            }
        });
        
    }

    public static void startup() {
        executor.execute(new Runnable() {
            
            public void run() {
                ZkClient zkCli = new ZkClient(host, port);
            }
        });
    }
}
