package com.lifujian.www.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author lifujian  2018年1月22日
 * @Description: 创建 znode
 */
public class ZKCreate {
    // create static instance for zookeeper class.
    private static ZooKeeper zk;

    // create static instance for ZooKeeperConnection class.
    private static ZooKeeperConnection conn;

    // Method to create znode in zookeeper ensemble
    public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
        
        /**
         * @param path
         * @param data
         * @param acl   the acl for the node   要创建的节点的访问控制列表。
         * @param createMode    specifying whether the node to be created is ephemeral and/or sequential
         *                      节点的类型，即临时，顺序或两者。这是一个枚举。
         *                      persistent - 客户端断开连接后，znode不会自动删除。
         *                      ephemeral - 临时
         */
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void main(String[] args) {

        // znode path
        String path = "/MyFirstZnode"; // Assign path to znode
//        String path = "/a/b/c"; //!!! 一级一级的创建,不能一下创建多级节点目录  KeeperErrorCode = NoNode for /a/b/c

        // data in byte array
        byte[] data = "My first zookeeper app".getBytes(); // Declare data

        try {
            conn = new ZooKeeperConnection();
            zk = conn.connect();
            create(path, data); // Create the data to the specified path
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Catch error message
        }
    }
}
