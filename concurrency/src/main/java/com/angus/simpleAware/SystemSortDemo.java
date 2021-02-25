package com.angus.simpleAware;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SystemSortDemo.java
 * @author angus
 * @version 1.0.0
 * @Description 用于代码张重排序的情况
 * @createTime 2021年02月25日 14:21:00
 */public class SystemSortDemo {
    /**
     * 可能性一：
     * 主要进行storeLoad重排序的测试
     * store - load为写-读操作
     * 由于多级缓存的存在会存在所以当存在
     * 1、写先写进了缓存
     * 2、读从主存读
     * 3、最后在刷新到贮存
     * 导致在主存角度就是指令进行了重新的排序。
     *
     * 可能性二：
     * volatile是可以保证可见性，主要是因为（1）总线锁 （2）缓存锁两种方式(无法锁住缓存的时候)
     * 其中开启的协议就是mesi协议volatile可以解决重排序，也就是因为其有内存屏障。
     * 若不存在内存屏障，mesi协议是存在问题的，也就是会导致
     * mesi有两个过程 （1）通知其他线程（2）挂起 (3)等待ask
     * 优化添加storebuffer之后，会存入buffer，不会挂起但是在等待ask可能会先进行下一个指令的导致重排序，
     * 这样也就保证不了可见性。所以volatile需要加上Lock
     *
     *
     * happens-before
     * 1、线程当前操作 -》线程后续操作
     * 2、解锁操作 -》加锁操作
     * 3、volatile写 -》volatile读
     * 4、A—》B B-》C  = A-》C
     *
     *
     */
    static int a = 0;
    static int b = 0;
    static int x = 0;
    static int y = 0;
    public static void main(String[] args) throws InterruptedException {



        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            //初始化两个值
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread thread = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread thread1 = new Thread(() -> {
                b = 2;
                y = a;
            });
            thread.start();
            thread1.start();
            thread.join();
            thread1.join();
            set.add("x = "+x+",y = "+y);
        }

        System.out.println(set);

    }

}
