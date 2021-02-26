package com.angus.basic;


import java.util.LinkedList;


/**
 * @author angus
 * @version 1.0.0
 * @ClassName WaitNotifyDemo.java
 * @Description 通过自己先些个例子，然后查看书进行学习
 * @createTime 2021年02月26日 15:06:00
 */
public class WaitNotifyDemo {

    /**
     * 实验题目：用Java的等待/通知机制实现“厨师—食 者”问题。假设分别有4位厨师和6位食者。
     * 厨师做一盘 菜的时间为4s，食者吃一盘菜的时间为3s。编程实现这 一功能，参考“生产者—消费者”问题。
     *
     * 该题目主要的就是他的资源就是放桌子的容器或者容器里的食物
     * 一、wait()和notify()问题
     * 【1】wait()
     * The current thread must own this object's monitor.
     * The thread releases ownership of this monitor and waits until
     * another thread notifies threads waiting on this object's monitor to wake up either
     * through a call to the notify method or the notifyAll method. The thread then waits
     * until it can re-obtain ownership of the monitor and resumes execution.
     * As in the one argument version, interrupts and spurious wakeups are possible,
     * and this method should always be used in a loop:
     *            synchronized (obj) {
     *                while (<condition does not hold>)
     *                    obj.wait();
     *                ... // Perform action appropriate to condition
     *            }
     *
     * 【2】notifyAll()
     *
     * Wakes up all threads that are waiting on this object's monitor.
     * A thread waits on an object's monitor by calling one of the wait methods.
     * The awakened threads will not be able to proceed until the current thread relinquishes
     * the lock on this object. The awakened threads will compete in the usual manner
     * with any other threads that might be actively competing to synchronize on this object;
     * for example, the awakened threads enjoy no reliable privilege or disadvantage
     * in being the next thread to lock this object.
     * This method should only be called by a thread that is the owner of this object's monitor.
     * See the notify method for a description of the ways in which a thread can become the owner of a monitor.
     *
     */


    /**
     * 该类为食物容器类
     */
    public static class container extends LinkedList<food>{
        int maxSize;
        public container(int maxSize){
            this.maxSize = maxSize;
        }

        /**
         *
         * @param food
         * @throws InterruptedException
         *
         * 此处synchronized锁住的是container,
         * 1、此时如果有一个线程进来发现容器已经满了，就会进入等待container的队列
         * 2、当添加食物之后会 notifyAll(),唤醒等待container的队列里的线程。
         * 3、由于释放的是持有锁的等待线程，所以会唤醒cooker或者comsumer。
         * 4、使用notifyAll()就可以最大限度通知到该通知的线程。
         */
        public synchronized void putFood(food food) throws InterruptedException {
            //在此处进行等待
            while (this.size()==maxSize){
                System.out.println("容器已经满了，停止制作！");
                wait();
            }
            this.add(food);
            notifyAll();
        }

        /**
         *
         * @return
         * @throws InterruptedException
         *
         * 此处synchronized锁住的是container
         *
         */
        public synchronized food getFood() throws InterruptedException {
            while (this.size() == 0){
                System.out.println("容器已经空了，停止吃！");
                wait();
            }
            food food = this.removeFirst();
            notifyAll();
            return food;
        }
    }

    /**
     * 该类为食物类
     */
    public static class food{

    }


    /**
     * 该类为厨师的实体类
     */
    public static class cooker{

        container container;

        cooker(container container){
            this.container = container;
        }

        public void cook() throws InterruptedException {
            while (true){
                //厨师用四秒做一道菜
                Thread.sleep(4000);
                System.out.println("厨师制作了一道菜！");
                container.putFood(new food());
            }

        }
    }

    /**
     * 该类为顾客实体类
     */
    public static class comsumer{
        container container;

        comsumer(container container){
            this.container = container;
        }
        public void eat() throws InterruptedException {
            while (true){
                //顾客用三秒吃一道菜
                food peek = container.getFood();
                        Thread.sleep(3000);
                        System.out.println("顾客吃了"+peek+"一道菜！");
                    }
                }
    }

    public static void main(String[] args) {
        container container = new container(6);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    new cooker(container).cook();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    new comsumer(container).eat();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

}
