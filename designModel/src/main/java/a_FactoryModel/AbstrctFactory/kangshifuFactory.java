package a_FactoryModel.AbstrctFactory;

import a_FactoryModel.product.*;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CoderFactory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:29:00
 */
public class kangshifuFactory implements AbstractFctory {


    @Override
    public food creatFood() {
        return new KangshifuFood();
    }

    @Override
    public drink creatDrink() {
        return new KangshifuDrink();
    }
}
