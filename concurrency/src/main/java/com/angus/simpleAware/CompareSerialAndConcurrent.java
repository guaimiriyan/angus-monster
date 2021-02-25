package com.angus.simpleAware;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CompareSerialAndConcurrent.java
 * @Description 测试是否并行和串行的执行时间比较
 * @createTime 2021年02月24日 14:13:00
 */
public class CompareSerialAndConcurrent {

    /**
     * 1、count = 10000
     * Concurrent spend time:0.099s
     * Serial spend time:0.0s
     *
     * 2、count = 100000
     * Concurrent spend time:0.07s
     * Serial spend time:0.003s
     *
     * 3、count = 1000000
     * Concurrent spend time:0.053s
     * Serial spend time:0.004s
     *
     * 4、count = 100000000
     *
     * 通过jmap -dump:format=b,file=C:\Users\31665\Desktop\dump1
     */
    private static final int EXE_COUNT = 2100000000;
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        //1、使用串行执行该方法
        concurrent();
        Thread.sleep(10000);
        //2、使用并行执行该方法
        serial();
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < EXE_COUNT; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < EXE_COUNT; i++) {
            b--;
        }
        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.println("Serial spend time:"+time+"s");
    }

    private static void concurrent() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < EXE_COUNT; i++) {
                a += 5;
            }
        });
        thread.setName("concurrent");
        thread.start();
        int b = 0;
        for (int i = 0; i < EXE_COUNT; i++) {
            b--;
        }
        thread.join();

        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.println("Concurrent spend time:"+time+"s");
    }
}
