package com.angus.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CyclicBarrierDemo.java
 * @Description TODO
 * @createTime 2021年03月01日 17:15:00
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        cyclicBarrier.await();
        /**
         *     private int dowait(boolean timed, long nanos)
         *         throws InterruptedException, BrokenBarrierException,
         *                TimeoutException {
         *                //CyclicBarrier使用重入锁进行的控制
         *                //使用的是非公平锁进行的控制
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             final Generation g = generation;
         *              //该属性应该是可以进行破坏该栅栏的方法
         *             if (g.broken)
         *                 throw new BrokenBarrierException();
         *
         *             if (Thread.interrupted()) {
         *                 breakBarrier();
         *                 throw new InterruptedException();
         *             }
         *
         *             int index = --count;
         *             if (index == 0) {  // tripped
         *                 boolean ranAction = false;
         *                 try {
         *                 //当为0时自动触发相应的功能，可以进行相应的处理
         *                     final Runnable command = barrierCommand;
         *                     if (command != null)
         *                         command.run();
         *                     ranAction = true;
         *                     //在这里进行下一次栅栏的初始化，可以进行重置
         *                     nextGeneration();
         *                     return 0;
         *                 } finally {
         *                     if (!ranAction)
         *                         breakBarrier();
         *                 }
         *             }
         *
         *
         *             // loop until tripped, broken, interrupted, or timed out
         *             //开始进行的thread在这里进入等待，等最后的nextGeneration()进行重置。
         *             for (;;) {
         *                 try {
         *                     if (!timed)
         *                         trip.await();
         *                     else if (nanos > 0L)
         *                         nanos = trip.awaitNanos(nanos);
         *                 } catch (InterruptedException ie) {
         *                     if (g == generation && ! g.broken) {
         *                         breakBarrier();
         *                         throw ie;
         *                     } else {
         *                         // We're about to finish waiting even if we had not
         *                         // been interrupted, so this interrupt is deemed to
         *                         // "belong" to subsequent execution.
         *                         Thread.currentThread().interrupt();
         *                     }
         *                 }
         *
         *                 if (g.broken)
         *                     throw new BrokenBarrierException();
         *
         *                 if (g != generation)
         *                     return index;
         *
         *                 if (timed && nanos <= 0L) {
         *                     breakBarrier();
         *                     throw new TimeoutException();
         *                 }
         *             }
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         *
         *
         */
    }
}
