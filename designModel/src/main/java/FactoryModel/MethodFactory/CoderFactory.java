package FactoryModel.MethodFactory;

import FactoryModel.product.Coder;
import FactoryModel.product.human;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CoderFactory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:29:00
 */
public class CoderFactory implements humanFctory{

    @Override
    public human trainingTalents() {
        return new Coder();
    }
}
