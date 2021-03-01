package com.angus.basic.selfThreadPool;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadPool.java
 * @Description 基本线程池接口
 * @createTime 2021年02月27日 14:31:00
 */
public interface ThreadPool<T extends Runnable> {

    //提交任务
    void submit(T t);

}
