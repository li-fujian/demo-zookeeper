package com.lifujian.www.register;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author lifujian  2018年1月20日
 * @Description: 
 */
public class ConnectedWatcher implements Watcher {

    private ZkClient zkCli;
    
    public ConnectedWatcher(ZkClient zkCli) {
        this.zkCli = zkCli;
    }
    
    public void process(WatchedEvent event) {
        switch (event.getState()) {
        
        case SyncConnected :
            
        }
    }

}
