package discovery;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ZkDiscovery.java
 * @Description TODO
 * @createTime 2021年05月04日 23:29:00
 */
public class ZkDiscovery implements AbstractDiscovery{

    List<String> serverCache;

    CuratorFramework curatorFramework;
    String zkAddress = "127.0.0.1:2181";
    {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(zkAddress)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .sessionTimeoutMs(1500)
                .namespace("registry")
                .build();
        curatorFramework.start();

    }

    /**
     * 1、需要初始化的时候获取
     */

    @Override
    public String discovery(String ServiceName) {
        try {
            if (serverCache == null){
                //进行初始化
                serverCache = curatorFramework.getChildren().forPath(ServiceName);
                //进行监控屏那个
                serviceWatcher(ServiceName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //此处使用不同策略进行
        LoadBalanceStrategy loadBalanceStrategy = new RandomLoadBalance();
        return loadBalanceStrategy.selectHost(serverCache);

    }

    private void serviceWatcher(String serviceName) throws Exception {
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, serviceName, true);
        pathChildrenCache.getListenable().addListener((curatorFramework1,pathChildrenCacheEvent)->{
            System.out.println("客户端收到节点变更的事件");
            serverCache=curatorFramework1.getChildren().forPath(serviceName);// 再次更新本地的缓存地址
        });
        pathChildrenCache.start();
    }
}
