package d_ProxyModel.dbDemo.proxy;

import d_ProxyModel.dbDemo.Order;
import d_ProxyModel.dbDemo.OrderDao;
import d_ProxyModel.dbDemo.OrderServiceImpl;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName OrderProxyTest.java
 * @Description TODO
 * @createTime 2021年03月13日 00:05:00
 */
public class OrderProxyTest {
    public static void main(String[] args) {
        OrderServiceStaticProxy orderServiceStaticProxy = new OrderServiceStaticProxy(new OrderServiceImpl(new OrderDao()));
        orderServiceStaticProxy.saveOrder(new Order("angus",20));
    }
}
