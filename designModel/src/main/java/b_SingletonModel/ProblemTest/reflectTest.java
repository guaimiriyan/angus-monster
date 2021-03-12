package b_SingletonModel.ProblemTest;

import b_SingletonModel.lazy.lazyInnerClassSingleton;

import java.lang.reflect.Constructor;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName reflectTest.java
 * @Description TODO
 * @createTime 2021年03月11日 16:55:00
 */
public class reflectTest {

    /**
     *
     * 通过在构造器中判断单例是否为空就可以防止反射进行
     * 1、对于饿汉式这一种方式是完全奏效的
     * 2、对于懒汉式来说并依赖于在反射的时候单例已经创建完成了
     * 3、对于内部类的懒汉式来说由于本质上是饿汉式，
     *    只是由于jvm内部类加载的机制进行的顺序的控制。
     */
    public static void main(String[] args) {
        try {
            Constructor<lazyInnerClassSingleton> constructor = lazyInnerClassSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            lazyInnerClassSingleton reflectInstance = constructor.newInstance();
            lazyInnerClassSingleton instance = lazyInnerClassSingleton.getInstance();
            System.out.println(instance == reflectInstance);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
