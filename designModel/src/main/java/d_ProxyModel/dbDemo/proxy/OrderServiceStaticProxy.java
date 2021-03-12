package d_ProxyModel.dbDemo.proxy;

import d_ProxyModel.dbDemo.Order;
import d_ProxyModel.dbDemo.OrderService;
import d_ProxyModel.dbDemo.db.dbSourceEntry;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName OrderServiceProxy.java
 * @Description TODO
 * @createTime 2021年03月13日 00:00:00
 */
public class OrderServiceStaticProxy implements OrderService {

    private OrderService orderService;

    public OrderServiceStaticProxy(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void saveOrder(Order order) {
        int length = order.getName().length();
        System.out.println("将数据源切换至 db_"+length);
        dbSourceEntry.setSource("db_"+length);
        orderService.saveOrder(order);
        dbSourceEntry.restore();
    }
}
