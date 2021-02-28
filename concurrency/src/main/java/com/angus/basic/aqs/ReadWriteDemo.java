package com.angus.basic.aqs;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ReadWriteDemo.java
 * @Description TODO
 * @createTime 2021年02月28日 13:42:00
 */
public class ReadWriteDemo {

    public static void main(String[] args) {
        //默认为非公平锁
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        for (int i = 0; i < 2; i++) {
            readLock.lock();
            //readLock.unlock();
        }



        /**
         *
         * 由于读锁为共享锁，所以lock调用的是acquireShared，
         * 获取的为共享状态。
         * protected final int tryAcquireShared(int unused) {
         *              Thread current = Thread.currentThread();
         *             int c = getState();
         *             //1、首先进行判断通过c&((1<<16)-1),可以得出写锁数量
         *             //如果写锁数量！=0，当前持有持有该锁的为自己可以进入获取读资源
         *             if (exclusiveCount(c) != 0 &&
         *                 getExclusiveOwnerThread() != current)
         *                 return -1;
         *             //2、获取当前获取都资源数量
         *             int r = sharedCount(c);
         *             //3、根据公平与非公平策略进行判断是否是可以进行获取到都资源
         *             if (!readerShouldBlock() &&
         *                 r < MAX_COUNT &&
         *                 compareAndSetState(c, c + SHARED_UNIT)) {
         *                 //4、cas成功获取读资源之后，进行数量的一系列计算
         *                 //第一个持有的读线程可直接进行计算
         *                 //其他的只能是通过
         *                 if (r == 0) {
         *                     firstReader = current;
         *                     firstReaderHoldCount = 1;
         *                 } else if (firstReader == current) {
         *                     firstReaderHoldCount++;
         *                 } else {
         *                     HoldCounter rh = cachedHoldCounter;
         *                     if (rh == null || rh.tid != getThreadId(current))
         *                         cachedHoldCounter = rh = readHolds.get();
         *                     else if (rh.count == 0)
         *                         readHolds.set(rh);
         *                     rh.count++;
         *                 }
         *                 return 1;
         *             }
         *             //5、到这里的是刚才由于【1】公平策略未轮上【2】或者cas失败的线程，【3】获取数量已经超了则进入
         *             return fullTryAcquireShared(current);
         *         }
         *
         *
         *  final int fullTryAcquireShared(Thread current) {
         *   HoldCounter rh = null;
         *
         *             for (;;) {
         *                 //1、获取目前读共享数量
         *                 int c = getState();
         *                 //2、判断是否有写存在
         *                 if (exclusiveCount(c) != 0) {
         *                     if (getExclusiveOwnerThread() != current)
         *                         return -1;
         *                     // else we hold the exclusive lock; blocking here
         *                     // would cause deadlock.
         *                     //只阻塞不是第一读，或者没有在读的线程
         *                 } else if (readerShouldBlock()) {
         *                     // Make sure we're not acquiring read lock reentrantly
         *                     if (firstReader == current) {
         *                         // assert firstReaderHoldCount > 0;
         *                     } else {
         *                         if (rh == null) {
         *                             rh = cachedHoldCounter;
         *                             if (rh == null || rh.tid != getThreadId(current)) {
         *                                 rh = readHolds.get();
         *                                 if (rh.count == 0)
         *                                     readHolds.remove();
         *                             }
         *                         }
         *                         if (rh.count == 0)
         *                             return -1;
         *                     }
         *                 }
         *                 //在此处可以再次进行获取
         *                 if (sharedCount(c) == MAX_COUNT)
         *                     throw new Error("Maximum lock count exceeded");
         *                 if (compareAndSetState(c, c + SHARED_UNIT)) {
         *                     if (sharedCount(c) == 0) {
         *                         firstReader = current;
         *                         firstReaderHoldCount = 1;
         *                     } else if (firstReader == current) {
         *                         firstReaderHoldCount++;
         *                     } else {
         *                         if (rh == null)
         *                             rh = cachedHoldCounter;
         *                         if (rh == null || rh.tid != getThreadId(current))
         *                             rh = readHolds.get();
         *                         else if (rh.count == 0)
         *                             readHolds.set(rh);
         *                         rh.count++;
         *                         cachedHoldCounter = rh; // cache for release
         *                     }
         *                     return 1;
         *                 }
         *             }
         *         }
         *
         *
         *    如下是readLock释放主要代码
         *    protected final boolean tryReleaseShared(int unused) {
         *             Thread current = Thread.currentThread();
         *             //首先对数值进行处理
         *             if (firstReader == current) {
         *                 // assert firstReaderHoldCount > 0;
         *                 if (firstReaderHoldCount == 1)
         *                     firstReader = null;
         *                 else
         *                     firstReaderHoldCount--;
         *             } else {
         *                 HoldCounter rh = cachedHoldCounter;
         *                 if (rh == null || rh.tid != getThreadId(current))
         *                     rh = readHolds.get();
         *                 int count = rh.count;
         *                 if (count <= 1) {
         *                     readHolds.remove();
         *                     if (count <= 0)
         *                         throw unmatchedUnlockException();
         *                 }
         *                 --rh.count;
         *             }
         *             //通过自旋的方式进行cas设置status
         *             for (;;) {
         *                 int c = getState();
         *                 int nextc = c - SHARED_UNIT;
         *                 if (compareAndSetState(c, nextc))
         *                     // Releasing the read lock has no effect on readers,
         *                     // but it may allow waiting writers to proceed if
         *                     // both read and write locks are now free.
         *                     return nextc == 0;
         *             }
         *         }
         *
         */


        for (int i = 0; i < 2; i++) {
            writeLock.lock();
            writeLock.unlock();

        }
        /**
         *
         *  protected final boolean tryAcquire(int acquires) {
         *
         *             Thread current = Thread.currentThread();
         *             int c = getState();
         *             //获取写状态
         *             int w = exclusiveCount(c);
         *             //1、若现在状态值不为0，必然有读或者有写
         *             if (c != 0) {
         *                 // (Note: if c != 0 and w == 0 then shared count != 0)
         *                 //2、如果没写（必然为有读），或者不是自己（不是自己在写）
         *                 if (w == 0 || current != getExclusiveOwnerThread())
         *                     return false;
         *                     //3、判断重入次数
         *                 if (w + exclusiveCount(acquires) > MAX_COUNT)
         *                     throw new Error("Maximum lock count exceeded");
         *                 // Reentrant acquire
         *                 //4、设置状态位
         *                 setState(c + acquires);
         *                 return true;
         *             }
         *             //若为0，可以进行判断是否有资格获取，或者是否获取成功
         *             if (writerShouldBlock() ||
         *                     !compareAndSetState(c, c + acquires))
         *                 return false;
         *             setExclusiveOwnerThread(current);
         *             return true;
         *         }
         *
         *
         *  protected final boolean tryRelease(int releases) {
         *                  //判断是否是自己的锁，释放别的线程是不行的，也不能释放null
         *             if (!isHeldExclusively())
         *                 throw new IllegalMonitorStateException();
         *             int nextc = getState() - releases;
         *             boolean free = exclusiveCount(nextc) == 0;
         *             if (free)
         *                 setExclusiveOwnerThread(null);
         *             setState(nextc);
         *             return free;
         *         }
         *
         *
         */



    }
}
