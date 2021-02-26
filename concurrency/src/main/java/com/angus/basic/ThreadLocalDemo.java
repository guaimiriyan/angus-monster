package com.angus.basic;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadLocalDemo.java
 * @Description TODO
 * @createTime 2021年02月26日 20:23:00
 */
public class ThreadLocalDemo {
    ThreadLocal<Long> local = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };
    /**
     * 解析threadLocal源码解析
     *
     *
     */
}
