package com.angus.simpleAware;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName DeadLockDemo.java
 * @Description 编写一个产生死锁的demo
 * @createTime 2021年02月24日 15:01:00
 */
public class DeadLockDemo {

    private static final Object A = new Object();
    private static final Object B = new Object();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        DeadLockDemo deadLockDemo = new DeadLockDemo();

        new Thread(()->{deadLockDemo.method1();},"angus1").start();
        new Thread(()->{deadLockDemo.method2();},"angus2").start();



    }

    private void method1(){
        synchronized (DeadLockDemo.A){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DeadLockDemo.B){
                System.out.println("method1此处进行操作");
            }
        }
    }

    private void method2(){
        synchronized (DeadLockDemo.B){
            synchronized (DeadLockDemo.A){
                System.out.println("method2此处进行操作");
            }
        }
    }

}
