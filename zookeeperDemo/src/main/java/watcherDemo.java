import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.Random;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName watcher.java
 * @Description TODO
 * @createTime 2021年05月01日 21:36:00
 */
@Slf4j
public class watcherDemo implements Watcher {

    ZooKeeper zk;
    String hostPort;
    static volatile boolean isLeader;
    watcherDemo(String port){
        this.hostPort = port;
    }
    public  void start() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);

    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }


    public boolean checkstat(){
        while (true){

            try {
                final Stat stat = new Stat();
                byte[] data = zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException e) {
            return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @title
     * @description 为zookeeper创建为主节点
     * @author admin
     * @updateTime 2021/5/1
     * @throws
     */
    String serverId = Integer.toHexString(new Random().nextInt());
    public void toBeMaster(){
        while (true){
            try {
                zk.create("/master",serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            } catch (KeeperException e) {
                System.out.println("kkkkkkkkkkkkkk");
               isLeader = false;
               break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (checkstat()) break;
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final watcherDemo watcherDemo = new watcherDemo("127.0.0.1:2181");
        watcherDemo.start();
        watcherDemo.toBeMaster();
        if (isLeader) {
            System.out.println("I'm the leader");
        // wait for a bit
        Thread.sleep(60000);
    } else {
        System.out.println("Someone else is the leader");
    }
//.stopZK();
        //Thread.sleep(12000);
        while (true){

        }
    }
}
