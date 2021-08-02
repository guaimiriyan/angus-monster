package com.angus.question;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName exchangePrint.java
 * @Description TODO
 * @createTime 2021年05月21日 10:18:00
 */
public class exchangePrint {
    /**
     * 使用两个线程交替答应
     */
    static  Object lock = new Object();
    public static void main(String[] args) {
        new Thread(()->{
//            try {
//                //Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            char c = 'a';
            for (int i = 0; i < 27; i++) {
                synchronized (lock){
                    try {
                        System.out.println((char)(c+i));
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 27; i++) {
                synchronized (lock){
                    try {
                        System.out.println(i);
                        lock.notify();
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
