package c_PrototypeModel.prototype;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Home.java
 * @Description TODO
 * @createTime 2021年03月12日 11:21:00
 */
public class Home implements Serializable,Cloneable{
    private String address;
    private int price;

    public Home(String address, int price) {
        this.address = address;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Home clone(){
        try {
            return (Home)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
