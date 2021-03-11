package FactoryModel.AbstrctFactory;

import FactoryModel.product.drink;
import FactoryModel.product.food;
import FactoryModel.product.human;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName humanFctory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:21:00
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
