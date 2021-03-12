package b_SingletonModel.lazy;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName lazyDoubleCheckSingleton.java
 * @Description TODO
 * @createTime 2021年03月11日 15:43:00
 */
public class lazyDoubleCheckSingleton {

    /**
     * 1、此处使用volatile是防止创建实例时的指令重排序，避免还未初始化的用户提前发布。
     * 2、未使用final是由于其他地方会修改
     */
    private static volatile lazyDoubleCheckSingleton instance = null;

    private lazyDoubleCheckSingleton(){
        if (instance != null){
            throw new RuntimeException("不允许创建多个实例!");
        }
    };

    public static lazyDoubleCheckSingleton getInstance(){
        /**
         * 如果不在此处进行空校验，与直接将sychronized放在方法上是一个道理，
         * 没有起到提高并发效率的作用。
         */
        if (instance == null){
            synchronized (lazyDoubleCheckSingleton.class){
                if (instance == null){
                    instance = new lazyDoubleCheckSingleton();
                    return instance;
                }
            }
        }
        return instance;
    }
}
