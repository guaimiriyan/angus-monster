package com.angus.question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName collection.java
 * @Description TODO
 * @createTime 2021年05月20日 21:34:00
 */
public class collection<T> {
    /**
     * 编写一个固定大小的容器，满足两个生产者，十个消费者
     */
    final int limitsize;
    public collection(int limitsize){
        this.limitsize =limitsize;
    }
    List<T> queque = new ArrayList<>();

    public synchronized void add(T t){
        try {
        if (queque.size()==limitsize){
            System.out.println("队列已经满了，不能add");
                this.wait();
        }
        //如果添加不满就可以进行添加
            System.out.println("添加任务"+t);
            queque.add(t);
            this.notifyAll();
        }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    public synchronized T take(){
        try {
            if (queque.size() == 0){
                System.out.println("队列为空，不能take");
                this.wait();
            }

            final T remove = queque.remove(0);
            System.out.println("消费任务"+remove);
            this.notifyAll();
            return remove;
        }catch (InterruptedException e){
            e.printStackTrace();
        }
       return null;
    }

    public static void main(String[] args) throws InterruptedException {
        final collection<Object> objectcollection = new collection<>(8);
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                while (true){
                    objectcollection.add(new Object());
                }
            }).start();
        }
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    objectcollection.take();
                }
            }).start();
        }
    }
}
