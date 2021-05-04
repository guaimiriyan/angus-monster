package register.center;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ZkRegisterCenter.java
 * @Description TODO
 * @createTime 2021年05月04日 22:22:00
 */
@Component
public class ZkRegisterCenter implements IRegisterCenter{

    String zkAddress = "127.0.0.1:2181";
    CuratorFramework curatorFramework;

    /**
     * 初始化zookpeer的客户端吧
     */
    {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(zkAddress)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .sessionTimeoutMs(1500)
                .namespace("registry")
                .build();
        curatorFramework.start();

    }

    @Override
    public void register(String serviceName,String hostPort) {
        //使用zk注册到,先判断服务节点是否存在于zk
        String zkServicePath = "/"+serviceName;
        try {
            if (curatorFramework.checkExists().forPath(zkServicePath) == null){
                //创建服务节点
                curatorFramework.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(zkServicePath);
            }
            //创建服务下的节点
            curatorFramework.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(zkServicePath+"/"+hostPort);
            System.out.println(serviceName+"/"+hostPort+"成功注册到zookpeer！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
