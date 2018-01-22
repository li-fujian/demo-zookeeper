package com.lifujian.www.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author lifujian  2018年1月22日
 * @Description: 删除节点
 */
public class ZKDelete {
    private static ZooKeeper zk;
    private static ZooKeeperConnection conn;

    // Method to check existence of znode and its status, if znode is available.
    public static void delete(String path) throws KeeperException,InterruptedException {
       //  如果给定的版本是-1，则它匹配任何节点的版本
       //  无法删除节点下还有子节点的非空节点 (KeeperErrorCode = Directory not empty for /MyFirstZnode)
       zk.delete(path, zk.exists(path, true).getVersion());
    }

    public static void main(String[] args) throws InterruptedException,KeeperException {
       String path = "/MyFirstZnod"; //Assign path to the znode
         
       try {
          conn = new ZooKeeperConnection();
          zk = conn.connect();
          delete(path); //delete the node with the specified path
       } catch(Exception e) {
          System.out.println(e.getMessage()); // catches error messages
       }
    }
}
