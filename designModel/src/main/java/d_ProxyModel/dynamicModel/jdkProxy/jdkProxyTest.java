package d_ProxyModel.dynamicModel.jdkProxy;

import d_ProxyModel.Angus;
import d_ProxyModel.beipiao;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName jdkProxyTest.java
 * @Description TODO
 * @createTime 2021年03月13日 00:17:00
 */
public class jdkProxyTest {
    public static void main(String[] args) {
        Jdkzhongjie jdkzhongjie = new Jdkzhongjie(new Angus());
        beipiao instance = jdkzhongjie.getInstance();
        instance.zhaofang();
        instance.zhaogongzuo();
    }
}
