package com.angus.basic.selfThreadPool;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ThreadPoolExecutorDemo.java
 * @Description TODO
 * @createTime 2021年03月02日 10:52:00
 */
public class ThreadPoolExecutorDemo {

    /**
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
     */
}
