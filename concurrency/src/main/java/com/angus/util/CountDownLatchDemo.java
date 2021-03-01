package com.angus.util;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CountDownLatchDemo.java
 * @Description
 * @createTime 2021年03月01日 16:17:00
 */
public class CountDownLatchDemo {
    /**
     * 实现一个容器，提供两个方法 add,size
     * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
     * 当个数到5个时，线程2给出提示并结束
     *
     * CountDownLatch也是使用AQS实现的只是使用了shrade的方式
     * Sync(int count) {
     *             setState(count);
     *         }
     *
     * 一、countdown实际调用的方法的每次将state进行减一操作
     * protected boolean tryReleaseShared(int releases) {
     *             // Decrement count; signal when transition to zero
     *             for (;;) {
     *                 int c = getState();
     *                 if (c == 0)
     *                     return false;
     *                 int nextc = c-1;
     *                 if (compareAndSetState(c, nextc))
     *                     return nextc == 0;
     *             }
     *         }
     *
     * 二、await方法主要是调用的tryaquire的方式进行
     *  protected int tryAcquireShared(int acquires) {
     *             return (getState() == 0) ? 1 : -1;
     *         }
     *  判断是否为零，判断返回值>0或者小于0
     *  如果小于0则走下面这个方法进行判断：
     *  private void doAcquireSharedInterruptibly(int arg)
     *         throws InterruptedException {
     *         //在这里将调用await的线程进行入队。
     *         final Node node = addWaiter(Node.SHARED);
     *         boolean failed = true;
     *         try {
     *             for (;;) {
     *                 final Node p = node.predecessor();
     *                 //如果是头节点之后的节点则，可以开始进行获取state
     *                 if (p == head) {
     *                     int r = tryAcquireShared(arg);
     *                     if (r >= 0) {
     *                         setHeadAndPropagate(node, r);
     *                         p.next = null; // help GC
     *                         failed = false;
     *                         return;
     *                     }
     *                 }
     *                 //如果不是就会进行park
     *                 if (shouldParkAfterFailedAcquire(p, node) &&
     *                     parkAndCheckInterrupt())
     *                     throw new InterruptedException();
     *             }
     *         } finally {
     *             if (failed)
     *                 cancelAcquire(node);
     *         }
     *     }
     *
     */

    static class angusList<T> extends ArrayList<T>{
        private CountDownLatch countDownLatch;

        public CountDownLatch getCountDownLatch() {
            return countDownLatch;
        }

        angusList(int latchSize){
            countDownLatch = new CountDownLatch(latchSize);
        }
        angusList(int latchSize,int size){
            super(size);
            countDownLatch = new CountDownLatch(latchSize);
        }
        boolean selfAdd(T t) throws InterruptedException {
            countDownLatch.countDown();
            System.out.println("添加了一个元素！");
            return add(t);
        }

    }

    public static void main(String[] args) {

        angusList angusList = new angusList(5);
        new Thread(()->{
            try {
                angusList.countDownLatch.await();
                System.out.println("已经添加了五个元素！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    angusList.selfAdd(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();



    }


}
