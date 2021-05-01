import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName masterAsyn.java
 * @Description TODO
 * @createTime 2021年05月02日 06:23:00
 */
@Log
public class masterAsyn implements Watcher {

    ZooKeeper zk;
    String hostPort;
    static volatile boolean isLeader;
    masterAsyn(String hostPort){
        this.hostPort = hostPort;
    }

    void start() throws IOException {
        zk = new ZooKeeper(hostPort,1500,this);
    }
    String serverId = Integer.toHexString(new Random().nextInt());

    AsyncCallback.StringCallback creatCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int i, String s, Object o, String s1) {
            /**
             * param1：异步调用的返回编码
             * param2：create的path参数值
             * param3：我们传给create的上下⽂参数
             * param4：创建的znode节点名称
             */
            log.info("接收到的数据，rc："+i+",path:"+s+",ctx:"+o+",name:"+s1);
            switch (KeeperException.Code.get(i)){
                case CONNECTIONLOSS:
                    //如果是丢失连接使用check触发重新连接或者其他操作
                    check();
                    return;
                case OK:
                 isLeader = true;
                 break;
                default:
                    isLeader = false;
            }
            System.out.println("I'm " + (isLeader ? "" : "not ") +
                    "the leader");
        }
    };




    void run(){

        //1、使用异步的方式创建主节点
        zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, EPHEMERAL,creatCallback,null);
    }

    AsyncCallback.DataCallback checkCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
            switch (KeeperException.Code.get(i)){
                case CONNECTIONLOSS:
                    //这里其实就是轮询
                    log.info("再次进行检查");
                    check();
                    return;
                case NONODE:
                    //重新竞争主节点
                    run();
                    return;
            }
        }
    };

    void check(){
        zk.getData("/master",false,checkCallback,null);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info(watchedEvent.toString());
    }

    public static void main(String[] args) {
        try {
            final masterAsyn masterAsyn = new masterAsyn("127.0.0.1:2181");
            masterAsyn.start();
            masterAsyn.run();
            while (true){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}