package a_FactoryModel.product;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Teacher.java
 * @Description TODO
 * @createTime 2021年03月11日 13:19:00
 */
public class Teacher implements human{
    @Override
    public void doWork() {
        System.out.println("我的工作是教书！");
    }
}
