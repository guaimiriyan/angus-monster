package com.angus.thick;

import java.math.BigInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName BetterFactorizer.java
 * @Description TODO
 * @createTime 2021年03月04日 17:05:00
 */
public class BetterFactorizer {
    /**
     * 该程序为并发编程事件中，通过内置锁解决的多个竞态变量问题
     * 但是该程序会有性能问题，导致每次只有一个线程能进入该方法
     * @ThreadSafe
     * public class SynchronizedFactorizer extends GenericServlet implements Servlet {
     *     @GuardedBy("this") private BigInteger lastNumber;
     *     @GuardedBy("this") private BigInteger[] lastFactors;
     *
     *     public synchronized void service(ServletRequest req,
     *                                      ServletResponse resp) {
     *         BigInteger i = extractFromRequest(req);
     *         if (i.equals(lastNumber))
     *             encodeIntoResponse(resp, lastFactors);
     *         else {
     *             BigInteger[] factors = factor(i);
     *             lastNumber = i;
     *             lastFactors = factors;
     *             encodeIntoResponse(resp, factors);
     *         }
     *     }
     * }
     * 分析：当线程进来的时候如果访问的缓存的数据就不用缓存
     *
     *    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
     *         ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
     *         ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
     *
     *     public synchronized void service(ServletRequest req,
     *                                      ServletResponse resp) {
     *
     *         BigInteger i = extractFromRequest(req);
     *         if (i.equals(lastNumber))
     *         readLock.lock();
     *             encodeIntoResponse(resp, lastFactors);
     *             readLock.unLock();
     *         else {
     *             BigInteger[] factors = factor(i);
     *             writeLock.lock();
     *             lastNumber = i;
     *             lastFactors = factors;
     *             writeLock.unLock();
     *             encodeIntoResponse(resp, factors);
     *         }
     *     }
     *
     *
     *
     */


    {


    }


}
