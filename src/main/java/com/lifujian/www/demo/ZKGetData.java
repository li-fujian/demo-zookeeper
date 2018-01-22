package com.lifujian.www.demo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author lifujian 2018年1月22日
 * @Description: 获取数据
 */
public class ZKGetData {
    private static ZooKeeper zk;
    private static ZooKeeperConnection conn;

    public static Stat znode_exists(String path) throws KeeperException, InterruptedException {
        return zk.exists(path, true);
    }

    /**
     * @param args
     * @throws InterruptedException
     * @throws KeeperException
     */
    public static void main(String[] args) throws InterruptedException, KeeperException {
        String path = "/MyFirstZnode";
        final CountDownLatch connectedSignal = new CountDownLatch(1);

        try {
            conn = new ZooKeeperConnection();
            zk = conn.connect();
            Stat stat = znode_exists(path);

            if (stat != null) {
                /**
                 * @param path
                 * @param watcher  监视器类型的回调函数。当指定的znode的数据改变时，ZooKeeper集合将通过监视器回调进行通知。这是一次性通知。
                 * @param stat    返回znode的元数据。
                 */ 
                byte[] b = zk.getData(path, new Watcher() {

                    public void process(WatchedEvent we) {

                        if (we.getType() == Event.EventType.None) {
                            switch (we.getState()) {
                            case Expired:
                                connectedSignal.countDown();
                                break;
                            default:
                                break;
                            }

                        } else {
                            String path = "/MyFirstZnode";

                            try {
                                byte[] bn = zk.getData(path, false, null);
                                String data = new String(bn, "UTF-8");
                                System.out.println(data);
                                connectedSignal.countDown();

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }, null);

                String data = new String(b, "UTF-8");
                System.out.println(data);
                connectedSignal.await();

            } else {
                System.out.println("Node does not exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
