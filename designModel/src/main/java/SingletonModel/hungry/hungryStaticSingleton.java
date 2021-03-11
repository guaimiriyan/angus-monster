package SingletonModel.hungry;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName hungryStaticSingleton.java
 * @Description TODO
 * @createTime 2021年03月11日 15:38:00
 */
public class hungryStaticSingleton {
    private static final hungryStaticSingleton instance;
    static {
        instance = new hungryStaticSingleton();
    }
    private  hungryStaticSingleton(){
        if (instance != null){
            throw new RuntimeException("不允许产生多个实例！");
        }
    };
    public static final hungryStaticSingleton getInstance(){
        return instance;
    }
}
