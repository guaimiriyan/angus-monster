package FactoryModel.AbstrctFactory;

import FactoryModel.product.*;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName TeacherFactory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:31:00
 */
public class WahahaFactory implements AbstractFctory {

    @Override
    public food creatFood() {
        return new WahahaFood();
    }

    @Override
    public drink creatDrink() {
        return new WahahaDrink();
    }
}
