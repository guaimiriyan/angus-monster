package FactoryModel.product;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName KangshifuFood.java
 * @Description TODO
 * @createTime 2021年03月11日 14:23:00
 */
public class KangshifuFood implements food{
    @Override
    public void creatFood() {
        System.out.println("我是康师傅的食品！");
    }
}
