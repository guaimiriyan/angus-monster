package com.angus.basic.cor;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName cordemo.java
 * @Description 基于责任链的方式，生成基于生产消费者异步执行责任的方式
 * @createTime 2021年04月08日 10:04:00
 */
public class cordemo {
    /**
     * cor代表的是chain of responsibility 也就是责任链模式
     */
    public static void main(String[] args) throws InterruptedException {

        /**
         * 问题分析
         * 使用接口的集合是一个集合所以不能放在接口里
         */
        final testPostProcessor testPostProcessor = new testPostProcessor(null);
        final codePostProcessor codePostProcessor = new codePostProcessor(testPostProcessor);
        final needPostProcessor needPostProcessor = new needPostProcessor(codePostProcessor);
        new Thread(needPostProcessor).start();
        new Thread(codePostProcessor).start();
        new Thread(testPostProcessor).start();
        needPostProcessor.process(new item("angus","lee"));
    }
}
