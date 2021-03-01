package com.angus.basic;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName JoinDemo.java
 * @Description 通过试题进行join的理解
 * @createTime 2021年02月26日 17:16:00
 */
public class JoinDemo {

    /**
     * 首先去看一下thread.join()的实际作用
     * Waits at most {@code millis} milliseconds for this thread to
     * die. A timeout of {@code 0} means to wait forever.
     *
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     *
     *  public final synchronized void join(long millis)
     *     throws InterruptedException {
     *         long base = System.currentTimeMillis();
     *         long now = 0;
     *
     *         if (millis < 0) {
     *             throw new IllegalArgumentException("timeout value is negative");
     *         }
     *
     *         if (millis == 0) {        //如果等待时间传入的是0，则一直等待
     *             while (isAlive()) {
     *                 wait(0);
     *             }
     *         } else {
     *             while (isAlive()) {
     *                 long delay = millis - now;
     *                 if (delay <= 0) {
     *                     break;
     *                 }
     *                 wait(delay);
     *                 now = System.currentTimeMillis() - base;
     *             }
     *         }
     *     }
     *
     * 如果多个线程同时调用 thread1.join，如果thread1还是isAlive状态
     * 他们都会被放入等待队列，但是没人唤醒啊。
     * 在线程thread1结束的时候会自动调用notifyAll
     *
     * 题目：
     * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
     *
     */

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new MyRunnale(null),"angus-1");
        Thread thread2 = new Thread(new MyRunnale(thread1));
        Thread thread3 = new Thread(new MyRunnale(thread2));
       thread2.start();
       thread1.start();
       thread3.start();



    }

    public static class MyRunnale implements Runnable{
        Thread thread;
        public MyRunnale(Thread thread ){
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                if (thread!=null){
                    thread.join();
                }
                System.out.println(Thread.currentThread().getName()+"执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
