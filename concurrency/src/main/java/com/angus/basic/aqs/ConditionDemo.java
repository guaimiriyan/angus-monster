package com.angus.basic.aqs;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ConditionDemo.java
 * @Description 查看Condition的源码分析
 * @createTime 2021年02月28日 18:43:00
 */
public class ConditionDemo {

    /**
     *
     *  public final void await() throws InterruptedException {
     *             if (Thread.interrupted())
     *                 throw new InterruptedException();
     *             Node node = addConditionWaiter();
     *             int savedState = fullyRelease(node);
     *             int interruptMode = 0;
     *             while (!isOnSyncQueue(node)) {
     *                 LockSupport.park(this);
     *                 if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
     *                     break;
     *             }
     *             if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
     *                 interruptMode = REINTERRUPT;
     *             if (node.nextWaiter != null) // clean up if cancelled
     *                 unlinkCancelledWaiters();
     *             if (interruptMode != 0)
     *                 reportInterruptAfterWait(interruptMode);
     *         }
     */
    public static void main(String[] args) {
        boolean flag = true;
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        lock.lock();
        try {
        while (flag){

                condition.await();
        }
        //进行业务操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
