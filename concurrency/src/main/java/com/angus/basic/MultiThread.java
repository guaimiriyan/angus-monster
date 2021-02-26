package com.angus.basic;

import com.sun.jmx.mbeanserver.MXBeanMappingFactory;

import javax.management.MXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName MultiThread.java
 * @Description
 * @createTime 2021年02月26日 10:38:00
 */
public class MultiThread {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo);
        }
    }

    /**
     *
     * "Thread-0" Id=12 TIMED_WAITING
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at com.angus.basic.MultiThread.lambda$main$0(MultiThread.java:21)
     * 	at com.angus.basic.MultiThread$$Lambda$1/1324119927.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "Monitor Ctrl-Break" Id=6 RUNNABLE
     * 	at java.net.URI.<init>(URI.java:588)
     * 	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:377)
     * 	at java.net.Socket.connect(Socket.java:606)
     * 	at java.net.Socket.connect(Socket.java:555)
     * 	at java.net.Socket.<init>(Socket.java:451)
     * 	at java.net.Socket.<init>(Socket.java:228)
     * 	at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:42)
     *
     *
     * "Attach Listener" Id=5 RUNNABLE
     *
     *  分发处理发给jvm信号的线程
     * "Signal Dispatcher" Id=4 RUNNABLE
     *
     * 调用对象Finalizer方法线程
     * "Finalizer" Id=3 WAITING on java.lang.ref.ReferenceQueue$Lock@1b6d3586
     * 	at java.lang.Object.wait(Native Method)
     * 	-  waiting on java.lang.ref.ReferenceQueue$Lock@1b6d3586
     * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
     * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
     * 	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
     *
     * 清除Reference线程
     * "Reference Handler" Id=2 WAITING on java.lang.ref.Reference$Lock@4554617c
     * 	at java.lang.Object.wait(Native Method)
     * 	-  waiting on java.lang.ref.Reference$Lock@4554617c
     * 	at java.lang.Object.wait(Object.java:502)
     * 	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
     * 	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
     *
     * 程序主入口
     * "main" Id=1 RUNNABLE
     * 	at sun.management.ThreadImpl.dumpThreads0(Native Method)
     * 	at sun.management.ThreadImpl.dumpAllThreads(ThreadImpl.java:454)
     * 	at com.angus.basic.MultiThread.main(MultiThread.java:20)
     */
}
