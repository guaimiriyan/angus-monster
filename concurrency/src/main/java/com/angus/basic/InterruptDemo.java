package com.angus.basic;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName InterruptDemo.java
 * @Description 对线程中断进行理解
 * @createTime 2021年02月26日 13:07:00
 */
public class InterruptDemo {

    /**
     *一、通过源码观察可发现
     *
     * 【1】Thread.interrupted()源码如下：
     *  public static boolean interrupted() {
     *         return currentThread().isInterrupted(true);
     *     }
     *
     *  private native boolean isInterrupted(boolean ClearInterrupted);
     *  1、获取当前线程
     *  2、将ClearInterrupted传为true，将中断标志去除
     *  3、返回中断标志
     *
     *【2】thread.isInterrupted()源码如下；
     *  public boolean isInterrupted() {
     *         return isInterrupted(false);
     *     }
     * 1、将ClearInterrupted传为false，不去除中断标志
     * 2、返回中断标志
     *
     *
     * 二、如何处理中断
     * 【1】查看JUC包中大部分代码的处理方式
     * 1、此为ReentrantLock
     *  public final boolean tryAcquireNanos(int arg, long nanosTimeout)
     *             throws InterruptedException {
     *         if (Thread.interrupted())
     *             throw new InterruptedException();
     *         return tryAcquire(arg) ||
     *             doAcquireNanos(arg, nanosTimeout);
     *     }
     *
     * 获取中断标志并恢复，然后进行线程中段的抛出
     *
     *
     * 三、如何安全且优雅的停止程序【借鉴java并发实战】
     *
     */
    public static void main(String[] args) {
        Thread.interrupted();
        Thread thread = new Thread(() -> {
            while (true) {
            }
        }, "angus-interrupt");
        thread.setDaemon(true);
        thread.isInterrupted();
    }
}
