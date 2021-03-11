package FactoryModel.product;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName WahahaDrink.java
 * @Description TODO
 * @createTime 2021年03月11日 14:26:00
 */
public class WahahaDrink implements drink{
    @Override
    public void creatDrink() {
        System.out.println("我是哇哈哈的饮料！");
    }
}
