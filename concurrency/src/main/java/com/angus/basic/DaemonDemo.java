package com.angus.basic;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName DaemonDemo.java
 * @Description 初步使用daemon线程
 * @createTime 2021年02月26日 11:05:00
 */

/**
 * 守护线程分析：
 * 1、在守护线程中先启动，如果守护线程不是while(true)
 * 或者某些条件下，daemon也是会结束的。
 * 2、通过jstack或者MxBean都可以得出这样的结论
 * 3、Daemon线程的子进程也是Daemon线程
 *
 * "angus-daemon-son" #13 daemon prio=5 os_prio=0 tid=0x000001225f7ea000 nid=0x371c runnable [0x00000003674ff000]
 *    java.lang.Thread.State: RUNNABLE
 *         at com.angus.basic.DaemonDemo.lambda$null$0(DaemonDemo.java:30)
 *         at com.angus.basic.DaemonDemo$$Lambda$2/2016912823.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * "angus-daemon" #12 daemon prio=5 os_prio=0 tid=0x000001225f7dd800 nid=0x24b8 runnable [0x00000003673ff000]
 *    java.lang.Thread.State: RUNNABLE
 *         at com.angus.basic.DaemonDemo.lambda$main$1(DaemonDemo.java:34)
 *         at com.angus.basic.DaemonDemo$$Lambda$1/1324119927.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * 4、Daemon线程会在没有一个用户线程之后自动退出。
 *
 */
public class DaemonDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            new Thread(()->{
                while (true){

                }
            },"angus-daemon-son").start();
            while (true){

            }
        },"angus-daemon");
        thread.setDaemon(true);
        thread.start();
        //thread.join();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        int daemonThreadCount = threadMXBean.getDaemonThreadCount();
        System.out.println("守护线程个数："+daemonThreadCount);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo);
        }
        while (true){}
    }
}
