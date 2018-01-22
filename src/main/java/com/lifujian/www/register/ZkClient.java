package com.lifujian.www.register;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.lifujian.www.util.ConfigUtils;

/**
 * @author lifujian  2018年1月20日
 * @Description: zk 客户端
 */
public class ZkClient {
    
    public static String zkPath = ConfigUtils.getStringConfig("service.registry.path");
    public static String group = ConfigUtils.getStringConfig("service.registry.group");
    public static String zkAddr = ConfigUtils.getStringConfig("zk.cluster");
    
    private String ip;
    
    private int port;
    
    private String path;  // "/develop/serviceRegistry/demo-zk/5/1.1.1.1:2181"
    
    private ZooKeeper zk;
    
    private String pid = null;
    
    public ZkClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.path = zkPath + "/" + group + "/" + ip + ":" + port;
    }
    
    public void startup() throws IOException {
        zk = new ZooKeeper(zkAddr, 5000, null);
        zk.register(new ConnectedWatcher(this));
    }
    
    public void shutdown() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void keepAlive() throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, null, new Stat());
        if (data != null) {
            String content = new String(data);
            String[] arr = content.split("_");
            if (arr.length == 2 && arr[1].equals(pid)) {
                
            }
        }
    }
    
    private String getPid() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        String processID = processName.substring(0, processName.indexOf('@'));
        return processID;
    }
}

