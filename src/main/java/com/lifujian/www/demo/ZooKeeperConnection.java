package com.lifujian.www.demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.lifujian.www.util.ConfigUtils;

/**
 * @author lifujian 2018年1月22日
 * @Description: 连接到ZooKeeper集合
 */
public class ZooKeeperConnection {

    public static String zkAddr = ConfigUtils.getStringConfig("zk.cluster");
    private ZooKeeper zoo;
    // 用于停止（等待）主进程，直到客户端与ZooKeeper集合连接。
    final CountDownLatch connectedSignal = new CountDownLatch(1);

    /**
     * @return   ZooKeeper集合对象
     */
    public ZooKeeper connect() throws IOException, InterruptedException {

        /**
         * @param connectString    "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002"
         * @param sessionTimeout    超时时间，单位毫秒
         * @param watcher    实现'监视器'界面的对象。ZooKeeper集合通过监视器对象返回连接状态。
         */
        zoo = new ZooKeeper(zkAddr, 5000, new Watcher() {
            
            // 一旦连接，会执行此回调
            public void process(WatchedEvent event) {
                if (event.getState() == KeeperState.SyncConnected) {
                    System.out.println("---------SyncConnected--------");
                    connectedSignal.countDown();  // 释放锁
                }
            }

        });

        connectedSignal.await();
        return zoo;
    }

    // Method to disconnect from zookeeper server
    public void close() throws InterruptedException {
        zoo.close();
    }

    public static void main(String[] args) {
        try {
            new ZooKeeperConnection().connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
