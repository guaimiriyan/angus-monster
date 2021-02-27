package com.angus.basic;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName WaitNotifyHasTimeDemo.java
 * @Description 带有超时机制的数据库wait机制
 * @createTime 2021年02月26日 21:41:00
 */
public class WaitNotifyHasTimeDemo {

    public Object result;

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyHasTimeDemo waitNotifyHasTimeDemo = new WaitNotifyHasTimeDemo();

        //waitNotifyHasTimeDemo.get(100);
        Object o = waitNotifyHasTimeDemo.getnew(1000);
        System.out.println(o);
    }

    /**
     * @param mills
     * @return
     * @throws InterruptedException
     *
     * 此时锁是锁住的this对象，当进入线程进入同步区发现result为null的时候，
     * 进入该等待队列，状态为waiting。
     */
    public synchronized Object get(long mills) throws InterruptedException {
        while (result == null){
            System.out.println("等待中...................");
            wait(1000);
        }
        return result;
    }

    /**
     *
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public synchronized Object getnew(long mills) throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start+mills;
        while (result == null&&(end - System.currentTimeMillis())>0){
            System.out.println("等待中...................");
            wait(mills);
        }
        return result;
    }
}
