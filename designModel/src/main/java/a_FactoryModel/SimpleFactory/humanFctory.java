package a_FactoryModel.SimpleFactory;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName humanFctory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:21:00
 */
public interface humanFctory {
    <T> T trainingTalents(Class<T> clazz);
}
