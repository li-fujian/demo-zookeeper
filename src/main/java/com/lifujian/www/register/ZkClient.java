package com.lifujian.www.register;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifujian.www.util.ConfigUtils;

/**
 * @author lifujian  2018年1月20日
 * @Description: zk 客户端
 */
public class ZkClient {
    
    private final static Logger logger = LoggerFactory.getLogger(ZkClient.class);
    
    public static String zkPath = ConfigUtils.getStringConfig("service.registry.path");
    public static String group = ConfigUtils.getStringConfig("service.registry.group");
    public static String zkAddr = ConfigUtils.getStringConfig("zk.cluster");
    
    private String ip;
    
    private int port;
    
    private String path;  // "/develop/serviceRegistry/demo-zk/5/1.1.1.1:2181"
    
    private ZooKeeper zk;
    
    private String pid = getPid();
    
    public ZkClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.path = zkPath + "/" + group + "/" + ip + ":" + port;
    }
    
    public void startup() throws IOException {
        // 连接到ZooKeeper集合
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
        // 判断节点是否存在数据
        Stat stat = zk.exists(path, true);
        if (stat != null) {
            // 是否是同一个会话
            try {
                byte[] data = zk.getData(path, null, new Stat());
                if (data != null) {
                    String content = new String(data);
                    String[] arr = content.split("_");
                    if (arr.length == 2 && arr[1].equals(pid)) {
                        return;
                    }
                }
            } catch(Exception e) {
                logger.debug("get znode data exception.", e);
            }
            
            // 不是即删除
            try {
                zk.delete(path, zk.exists(path, false).getVersion());
            } catch(Exception e) {
                logger.debug("delete znode exception.", e);
            }
            
        } else {
            // 创建 znode
            String content = ip + ":" + port + "_" + pid;
            logger.debug("create znode. path is [{}], data is [{}].", path, content);
            zk.create(path, content.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        }

    }
    
    private String getPid() {
        try {
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            logger.debug("get process name. is [{}].", processName);
            String processID = processName.substring(0, processName.indexOf('@'));
            return processID;
        } catch(Exception e) {
            return null;
        }
    }

}

