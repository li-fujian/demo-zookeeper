package com.lifujian.www.register;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lifujian  2018年1月20日
 * @Description: 
 */
public class ConnectedWatcher implements Watcher {
    
    private final static Logger logger = LoggerFactory.getLogger(ConnectedWatcher.class);

    private ZkClient zkCli;
    
    public ConnectedWatcher(ZkClient zkCli) {
        this.zkCli = zkCli;
    }
    
    public void process(WatchedEvent event) {
        logger.debug("watcher process.");
        switch (event.getState()) {
        
        case SyncConnected :
            logger.debug("watcher process. is SyncConnected.");
            try {
                zkCli.keepAlive();
            } catch (Exception e) {
                e.printStackTrace();
                zkCli.shutdown();
            }
            
        case Disconnected :
            logger.debug("watcher process. is Disconnected.");
            break;
            
        case Expired :
            logger.debug("watcher process. is Expired.");
            zkCli.shutdown();
            break;
        
        case ConnectedReadOnly :
            logger.debug("watcher process. is ConnectedReadOnly.");
            break;
            
        case AuthFailed :
            logger.debug("watcher process. is AuthFailed.");
            break;

        default:
            break;
        }
    }

}
