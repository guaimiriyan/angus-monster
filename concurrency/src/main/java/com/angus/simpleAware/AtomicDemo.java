package com.angus.simpleAware;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AtomicDemo.java
 * @Description 主要理解原子操作
 * @createTime 2021年02月25日 10:52:00
 */
public class AtomicDemo {
    int count = 0;
    AtomicInteger atomic = new AtomicInteger(0);

    /**
     *原子性操作中java实现原子性操作的方式
     * 一、通过自旋cas
     * 问题：1、ABA问题 2、自旋带来的占用cpu但不干事 3、只能保证一个值本身的原子性
     * 相当于只是保证了取值->修改-》更新的操作变为原子性，代码块中无法保证。
     * 二、通过锁来进行原子性操作
     *
     */
    public static void main(String[] args) throws InterruptedException {
        AtomicDemo atomicDemo = new AtomicDemo();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ts.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        for (;;){
                            int num =  atomicDemo.atomic.get();
                            boolean b = atomicDemo.atomic.compareAndSet(num, ++num);
                            if (b){
                                break;
                            }
                        }

                        atomicDemo.count ++;
                    }
                }
            }));
        }
        long start = System.currentTimeMillis();
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }

        System.out.println(System.currentTimeMillis()-start);
        System.out.println(atomicDemo.atomic);
        System.out.println(atomicDemo.count);
    }
}
