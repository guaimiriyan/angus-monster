package a_FactoryModel.SimpleFactory;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName humanFactory.java
 * @Description TODO
 * @createTime 2021年03月11日 13:20:00
 */
public class simpleHumanFactory implements humanFctory {

    @Override
    public <T> T trainingTalents(Class<T> clazz) {
        if (clazz != null){
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
