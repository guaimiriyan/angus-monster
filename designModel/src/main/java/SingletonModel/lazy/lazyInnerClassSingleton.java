package SingletonModel.lazy;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName lazyInnerClassSingleton.java
 * @Description 利用匿名内部类的延迟加载，进行懒汉式单例的创建，
 *              可以在不使用锁的形似就可以进行单例的创建，
 *              但是无法避免反射的形式所避免。
 * @createTime 2021年03月11日 15:55:00
 */
public class lazyInnerClassSingleton {

    /**
     *防止反射去实例化一个单例
     */
    private lazyInnerClassSingleton(){
        if (InstanceHolder.instance != null){
          throw  new RuntimeException("不允许创建多个实例！");
        }
    }

    public static final lazyInnerClassSingleton getInstance(){
        return InstanceHolder.instance;
    }

    private static class InstanceHolder{
        /**
         * 虽然此处是饿汉式，但是InstanceHolder是在上方调用
         * InstanceHolder.instance时才会进行初始化
         */
        private static final lazyInnerClassSingleton instance =  new lazyInnerClassSingleton();

    }

//    static {
//        System.out.println("static{}");
//    }
//    {
//        System.out.println("{}");
//    }
//
//    private static class innerClass{
//        static {
//            System.out.println("inner class static{}");
//        }
//        {
//            System.out.println("inner class {}");
//        }
//
//        public static void method(){
//            System.out.println("inner class method!");
//        }
//    }
//
//    public static void main(String[] args) {
//        lazyInnerClassSingleton lazyInnerClassSingleton = new lazyInnerClassSingleton();
//        innerClass.method();
//    }
//
//    public void  method(){
//        innerClass.method();
//    }
}
