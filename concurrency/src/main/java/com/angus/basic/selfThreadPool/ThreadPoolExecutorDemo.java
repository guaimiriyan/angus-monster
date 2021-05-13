package com.angus.basic.selfThreadPool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadPoolExecutorDemo.java
 * @Description 解析ThreadPoolExecutor线程池
 * @createTime 2021年03月01日 21:30:00
 */
public class ThreadPoolExecutorDemo {


    public static void main(String[] args) {
        //计算
        int i = 1 << 29;
        String s = Integer.toBinaryString(i);
        //计算
        int i2 = (~((1 << 29) - 1));
        s= Integer.toBinaryString(i2);
        System.out.println(s);
        /**
         * public ThreadPoolExecutor(int corePoolSize, //线程池核心线程数
         *                               int maximumPoolSize, //线程池最大线程数
         *                               long keepAliveTime, //
         *                               TimeUnit unit,      //时间单位
         *                               BlockingQueue<Runnable> workQueue,//工作队列
         *                               ThreadFactory threadFactory, //创建线程的工厂
         *                               RejectedExecutionHandler handler) //拒绝策略
         *
         *
         * 一、KeepAliveTime
         * keepAliveTime：线程空闲时间。线程池中线程空闲时间达到keepAliveTime值时，
         * 线程会被销毁，只到剩下corePoolSize个线程为止。默认情况下，线程池的最大线程数大于corePoolSize时，
         * keepAliveTime才会起作用。如果allowCoreThreadTimeOut被设置为true，即使线程池的最大线程数等于corePoolSize，
         * keepAliveTime也会起作用（回收超时的核心线程）。
         *
         * 二、创建工厂
         * 用来生产一组相同任务的线程。主要用于设置生成的线程名词前缀、
         * 是否为守护线程以及优先级等。设置有意义的名称前缀有利于在进行虚拟机分析时，
         * 知道线程是由哪个线程工厂创建的。
         *
         * 三、拒绝策略
         *
         * 拒绝策略，当线程池中已经存在最了maximumPoolSize时，就会进行调用这个策略;
         *
         * 其中存在几个线程池中提供的
         * AbortPolicy：默认，丢弃任务并抛出RejectedExecutionException异常。
         * DiscardPolicy：丢弃任务，但是不抛出异常（不推荐）。
         * DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加入队列中。
         * CallerRunsPolicy：调用任务的run()方法绕过线程池直接执行。
         *
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                1000l,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "ansgus" + new Random().nextInt(20));
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                });

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池execute方法解析");
            }
        });

        /**
         *
         *  public void execute(Runnable command) {
         *             if (command == null)
         *                 throw new NullPointerException();
         *
         *             //private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
         *             //private static final int RUNNING    = -1 << COUNT_BITS;
         *             //private static int ctlOf(int rs, int wc) { return rs | wc; }
         *             //COUNT_BITS为29，计算-1二进制，源码10000000000000000000000000000001
         *             //反码:1111 1110  补码:1111 1111
         *             //11100000000000000000000|00000000000000000000000000
         *             ///111000.........00000000
         *             int c = ctl.get();
         *             //private static int workerCountOf(int c)  { return c & CAPACITY; }
         *             // private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
         *             //00100000000000000000000000000000-1=00011111.......11111111
         *             //这样后后面29位都可以进行run的线程书数进行分析
         *             if (workerCountOf(c) < corePoolSize) {
         *             //1、如果小于核心线程数，则添加worker进行直接run
         *                 if (addWorker(command, true))
         *                     return;
         *                 c = ctl.get();
         *             }
         *             //还在运行，且放入队列成功
         *             if (isRunning(c) && workQueue.offer(command)) {
         *                 int recheck = ctl.get();
         *                 //是否运行
         *                 if (! isRunning(recheck) && remove(command))
         *                     reject(command);
         *                  //2、没有worker就无法进行任务执行，所以new 一个
         *                  //会自动获取队列里的任务进行执行
         *                 else if (workerCountOf(recheck) == 0)
         *                     addWorker(null, false);
         *             }
         *             //3、
         *             else if (!addWorker(command, false))
         *                 reject(command);
         *         }
         *
         * 二、解析addWorker方法
         *  private boolean addWorker(Runnable firstTask, boolean core) {
         *         retry:
         *         for (;;) {
         *             int c = ctl.get();
         *             //获取前三位运行状态
         *             int rs = runStateOf(c);
         *
         *             // Check if queue empty only if necessary.
         *             //前三位状态>=shutDown说明已经进入不运行状态
         *             //但是在停止|且队列里有任务时，需要继续执行将队列里的任务给执行完成
         *             if (rs >= SHUTDOWN &&
         *                 ! (rs == SHUTDOWN &&
         *                    firstTask == null &&
         *                    ! workQueue.isEmpty()))
         *                 return false;
         *
         *              //循环判断并获取添加woker机会
         *             for (;;) {
         *                  //获取worker的个数
         *                 int wc = workerCountOf(c);
         *                 if (wc >= CAPACITY ||
         *                     wc >= (core ? corePoolSize : maximumPoolSize))
         *                     return false;
         *                 if (compareAndIncrementWorkerCount(c))
         *                     break retry;
         *                 c = ctl.get();  // Re-read ctl
         *                 if (runStateOf(c) != rs)
         *                     continue retry;
         *                 // else CAS failed due to workerCount change; retry inner loop
         *             }
         *         }
         *
         *         //进行worker的创建
         *         boolean workerStarted = false;
         *         boolean workerAdded = false;
         *         Worker w = null;
         *         try {
         *             w = new Worker(firstTask);
         *             final Thread t = w.thread;
         *             if (t != null) {
         *                 final ReentrantLock mainLock = this.mainLock;
         *                 //获取基于线程池的锁
         *                 mainLock.lock();
         *                 try {
         *                     // Recheck while holding lock.
         *                     // Back out on ThreadFactory failure or if
         *                     // shut down before lock acquired.
         *                     int rs = runStateOf(ctl.get());
         *
         *                     if (rs < SHUTDOWN ||
         *                         (rs == SHUTDOWN && firstTask == null)) {
         *                         if (t.isAlive()) // precheck that t is startable
         *                             throw new IllegalThreadStateException();
         *                             //添加worker到队列中进行管理
         *                         workers.add(w);
         *                         int s = workers.size();
         *                         if (s > largestPoolSize)
         *                             largestPoolSize = s;
         *                         workerAdded = true;
         *                     }
         *                 } finally {
         *                     mainLock.unlock();
         *                 }
         *                 if (workerAdded) {
         *                 //添加成功进行运行worker，去执行runnble
         *                     t.start();
         *                     workerStarted = true;
         *                 }
         *             }
         *         } finally {
         *             if (! workerStarted)
         *             //失败则
         *                 addWorkerFailed(w);
         *         }
         *         return workerStarted;
         *     }
         *
         *
         *     三、工程队列运行
         *      【1】public void run() {
         *             runWorker(this);
         *         }
         *
         *
         *     【2】worker基于AQS进行同步控制
         *     protected boolean tryAcquire(int unused) {
         *             if (compareAndSetState(0, 1)) {
         *                 setExclusiveOwnerThread(Thread.currentThread());
         *                 return true;
         *             }
         *             return false;
         *         }
         *
         *         protected boolean tryRelease(int unused) {
         *             setExclusiveOwnerThread(null);
         *             setState(0);
         *             return true;
         *         }
         *
         *     【3】真正运行的方法
         *     final void runWorker(Worker w) {
         *         Thread wt = Thread.currentThread();
         *         Runnable task = w.firstTask;
         *         w.firstTask = null;
         *         w.unlock(); // allow interrupts
         *         boolean completedAbruptly = true;
         *         try {
         *             while (task != null || (task = getTask()) != null) {
         *                 w.lock();
         *                 // If pool is stopping, ensure thread is interrupted;
         *                 // if not, ensure thread is not interrupted.  This
         *                 // requires a recheck in second case to deal with
         *                 // shutdownNow race while clearing interrupt
         *                 if ((runStateAtLeast(ctl.get(), STOP) ||
         *                      (Thread.interrupted() &&
         *                       runStateAtLeast(ctl.get(), STOP))) &&
         *                     !wt.isInterrupted())
         *                     wt.interrupt();
         *                 try {
         *                 //执行前置接口
         *                     beforeExecute(wt, task);
         *                     Throwable thrown = null;
         *                     try {
         *                         task.run();
         *                     } catch (RuntimeException x) {
         *                         thrown = x; throw x;
         *                     } catch (Error x) {
         *                         thrown = x; throw x;
         *                     } catch (Throwable x) {
         *                         thrown = x; throw new Error(x);
         *                     } finally {
         *                     //执行后置接口
         *                         afterExecute(task, thrown);
         *                     }
         *                 } finally {
         *                     task = null;
         *                     w.completedTasks++;
         *                     w.unlock();
         *                 }
         *             }
         *             completedAbruptly = false;
         *         } finally {
         *             processWorkerExit(w, completedAbruptly);
         *         }
         *     }
         *
         *     【4】获取任务
         *      private Runnable getTask() {
         *         boolean timedOut = false; // Did the last poll() time out?
         *
         *         for (;;) {
         *             int c = ctl.get();
         *             int rs = runStateOf(c);
         *
         *             // Check if queue empty only if necessary.
         *             //判断是否为停止状态或者队列为空直接返回，worker--
         *             if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
         *                 decrementWorkerCount();
         *                 return null;
         *             }
         *
         *             int wc = workerCountOf(c);
         *
         *             // Are workers subject to culling?
         *             boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
         *             //1、是否大于最大线程数
         *             //2、timed是否核心线程也一样释放，是否是在时间内获取失败
         *             //&&
         *             //1、保证有运行的线程
         *             //2、工作线程为空
         *             //结果：才释放worker
         *             if ((wc > maximumPoolSize || (timed && timedOut))
         *                 && (wc > 1 || workQueue.isEmpty())) {
         *                 if (compareAndDecrementWorkerCount(c))
         *                     return null;
         *                 continue;
         *             }
         *
         *             try {
         *                 Runnable r = timed ?
         *                 //时间需求
         *                     workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
         *                     //阻塞等待
         *                     workQueue.take();
         *                 if (r != null)
         *                     return r;
         *                 timedOut = true;
         *             } catch (InterruptedException retry) {
         *                 timedOut = false;
         *             }
         *         }
         *     }
         *
         *
         *     【5】关闭的区别
         *     1、shutdown只是将线程池的状态设置为SHUTWDOWN状态，正在执行的任务会继续执行下去，没有被执行的则中断。
         *     2、而shutdownNow则是将线程池的状态设置为STOP，正在执行的任务则被停止，没被执行任务的则返回。
         *
         */


    }
}
