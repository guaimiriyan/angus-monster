package a_FactoryModel.AbstrctFactory;

import a_FactoryModel.product.drink;
import a_FactoryModel.product.food;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName humanFctory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:21:00
 *
 * 抽象工厂模式是将产品簇和产品等级结构之之间的构造
 */

public interface AbstractFctory {
    food creatFood();
    drink creatDrink();

    public static AbstractFctory creatFactory(String name){
        if (name.equalsIgnoreCase("kangshifu")){
            return new kangshifuFactory();
        }else if (name.equalsIgnoreCase("Wahaha")){
            return new WahahaFactory();
        }else {
            throw new IllegalArgumentException("未使用正确名称");
        }
    }
}
