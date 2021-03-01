package com.angus.basic.selfThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName DefaultThreadPool.java
 * @Description TODO
 * @createTime 2021年02月27日 14:34:00
 */

public class DefaultThreadPool<T extends Runnable> implements ThreadPool<T>{

    /**
     * 总结：
     * 【1】未在初始化时进行线程启动，没有想清楚works的作用
     * 【2】未在添加任务时进行notify，这样就无法唤醒wait();
     * 【3】没有对中断进行出力
     */

    //线程池最大线程数
    private static final int MAX_THREAD_NUM = 10;
    private static final int DEFAULT_THREAD_NUM = 5;
    private static final int MIN_THREAD_NUM = 1;

    //线程池工作线程的大小
    private int poolSize;

    //线程总共的job数
    private int size = 0;

    DefaultThreadPool(){
        this.poolSize = DEFAULT_THREAD_NUM;
        initWorks(this.poolSize);
    }


    DefaultThreadPool(int poolSize){
        this.poolSize = poolSize>=MAX_THREAD_NUM? MAX_THREAD_NUM:poolSize<=MIN_THREAD_NUM?MIN_THREAD_NUM:poolSize;
        initWorks(this.poolSize);
    }

    //将任务放在List里进行获取
    List<T> jobs = new LinkedList<>();

    //还有就是创建的工作任务
    List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    /**
     * 进行工作线程的获取
     * @param poolSize
     */
    private void initWorks(int poolSize) {
        //往线程里初始化工作线程
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker).start();
        }
    }


    public void submit(T t) {
        synchronized (jobs){
            jobs.add(t);
            notify();
        }

    }

    private class Worker implements Runnable{

        public volatile boolean isRun = true;

        @Override
        public void run() {
            while (isRun){
                //1、判断
                T job = null;
                synchronized (jobs){
                    while (jobs.size() == 0){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //清楚interrupt标志
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                     job = jobs.remove(0);
                }

                if (job!=null){
                    job.run();
                }
            }


        }
    }
}
