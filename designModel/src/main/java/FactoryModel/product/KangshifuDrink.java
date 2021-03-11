package FactoryModel.product;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName KangshifuDrink.java
 * @Description TODO
 * @createTime 2021年03月11日 14:24:00
 */
public class KangshifuDrink implements drink{
    @Override
    public void creatDrink() {
        System.out.println("我是康师傅饮料！");
    }
}
