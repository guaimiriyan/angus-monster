package b_SingletonModel.hungry;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName hungrySingleton.java
 * @Description 饿汉式单例模式，意思就是在静态代码块中使用在类加载的时候
 *              就将单例进行创建，使用static final 进行修饰
 * @createTime 2021年03月11日 15:31:00
 */
public class hungrySingleton implements Serializable {
    /**
     * 1、使用static关键字使对象在类创建时就创建
     * 2、final是让别的对象的方法无法使用反射将其改变
     * 3、private能让单例对象封装
     */
    private static final hungrySingleton instance = new hungrySingleton();

    /**
     * 私有化够着函数，让其他类或方法无法进行new创建
     */
    private hungrySingleton(){};

    public static hungrySingleton getInstance(){
        return instance;
    }
}
