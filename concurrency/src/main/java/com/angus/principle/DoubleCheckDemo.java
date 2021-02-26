package com.angus.principle;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName DoubleCheckDemo.java
 * @Description 主要是在创建单例时解决并发问题
 * @createTime 2021年02月26日 09:39:00
 */
public class DoubleCheckDemo {

    private static singleton demo;


    /**
     *该方法会存在什么问题
     *如果在多线程情况下进行该方法调用,同时进行【1】，导致大量的对象创建
     */
    public static singleton getSingleton(){
        if (demo == null){
             demo = new singleton(); //1
             return demo;//2
        }
        return demo;
    }

    /**
     *该方法会存在什么问题？
     *将整个方法锁住导致在多线程情况下只有一个线程能获取到，
     *这种方法做会造成大量的延迟，所以不够好。
     */
    public static synchronized singleton getSingleton2(){
        if (demo == null){
             demo = new singleton(); //1
             return demo;//2
        }
        return demo;
    }

    /**
     *
     * 相较于上一种的优化是在于减小同步块的作用域。
     * 这种方式会导致其他线程和当前在同步块里的线程
     * 1、同时进入第一层检查（demo = null）
     * 2、其他线程在synchronized等待
     * 3、这些线程重新获取锁之后，就会无法保证单例
     *
     */
    public static singleton getSingleton3(){
        if (demo == null){
            synchronized (DoubleCheckDemo.class){
                demo = new singleton(); //1
                return demo;//2
            }
        }
        return demo;
    }

    /**
     * 此时demo仍然是一个非volatile变量
     * 1、改善了上一个方法进行双检查
     * 2、此时会出现的问题 demo引用提前赋值的问题
     *   （1）分配内存 （2）调用init方法 （3）给引用赋值
     *   JIT优化后：
     *   （1）分配内存 （3）给引用赋值（2）调用init方法
     * 3、导致demo = 未完全初始化的对象。会发布一个未完全的对象
     *
     * 解决问题：
     * 将单例申明volatile，可以保证指令不进行重排序且保证可见性
     */
    public static singleton getSingleton4(){
        if (demo == null){
            synchronized (DoubleCheckDemo.class){
                if (demo == null){
                    demo = new singleton(); //1
                    //在此中间进行内存屏障
                    //storeLoad 此后的读都依赖当前的写
                    return demo;//2
                }

            }
        }
        //在这里获取的时候就能获取最新的值
        return demo;
    }

}
