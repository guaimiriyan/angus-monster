package com.angus.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName LockSupportDemo.java
 * @Description TODO
 * @createTime 2021年05月20日 17:42:00
 */
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println(i);
                    if (i== 5) LockSupport.park();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        t.start();
        TimeUnit.MILLISECONDS.sleep(8000);
        LockSupport.unpark(t);
    }
}
