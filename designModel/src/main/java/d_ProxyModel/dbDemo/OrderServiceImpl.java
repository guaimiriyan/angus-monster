package d_ProxyModel.dbDemo;

import d_ProxyModel.dbDemo.OrderService;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName OrderServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月12日 23:55:00
 */
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void saveOrder(Order order) {
       orderDao.insertOrder(order);
    }
}
