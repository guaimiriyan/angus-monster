package d_ProxyModel.dbDemo;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Order.java
 * @Description TODO
 * @createTime 2021年03月12日 23:53:00
 */
public class Order {
    private String name;
    private int price;

    public Order(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
