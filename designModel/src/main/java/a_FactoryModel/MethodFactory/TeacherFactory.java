package a_FactoryModel.MethodFactory;

import a_FactoryModel.product.Teacher;
import a_FactoryModel.product.human;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName TeacherFactory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:31:00
 */
public class TeacherFactory implements humanFctory{
    @Override
    public human trainingTalents() {
        return new Teacher();
    }
}
