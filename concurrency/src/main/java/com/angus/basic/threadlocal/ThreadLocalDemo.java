package com.angus.basic.threadlocal;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ThreadLocalDemo.java
 * @Description TODO
 * @createTime 2021年02月26日 20:23:00
 */
public class ThreadLocalDemo {
    ThreadLocal<Long> local = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };


    /**
     * 解析threadLocal源码解析
     *
     * 一、查询解析
     *
     * 主要步骤：
     * 【1】获取当前线程
     * 【2】获取线程中属性threadLocals
     * 【3】通过该map获取Entry
     *      （1）通过threadLocal计算hash值
     *      （2）寻找该threadLocal对应的Entry
     *              a.使用hash直接找到
     *              b.开放寻址继续寻找
     *      （3）返回值
     * 【4】若未查找到进行初始化
     *
     *
     * Returns the value in the current thread's copy of this
     * thread-local variable.  If the variable has no value for the
     * current thread, it is first initialized to the value returned
     * by an invocation of the {@link #initialValue} method.
     *
     * public T get() {
     *         //1、首先获取当前使用该thread-Local的线程
     *         Thread t = Thread.currentThread();
     *         //2、获取内部类对象，ThreadLocalMap
     *         ThreadLocalMap map = getMap(t);
     *         if (map != null) {
     *             ThreadLocalMap.Entry e = map.getEntry(this);
     *             if (e != null) {
     *                 @SuppressWarnings("unchecked")
     *                 //3、获取到Entry的时候直接就进行数值返回
     *                 T result = (T)e.value;
     *                 return result;
     *             }
     *         }
     *         //4、若上面不成立就需要初始化,在没有则初始化
     *         //thread.threadLocals = new ThreadLocalMap();
     *         //使用initvalue进行数据初始化。
     *         return setInitialValue();
     *     }
     *
     *
     *  private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
     *              //1、当getEntry方法获取的直接hash的table[i]不是存的当前的threadLocal
     *             Entry[] tab = table;
     *             int len = tab.length;
     *
     *             while (e != null) {
     *                 ThreadLocal<?> k = e.get();
     *                 if (k == key)
     *                     return e;
     *                 if (k == null)
     *                     expungeStaleEntry(i);
     *                 else
     *                      //2、进行下一个Entry进行寻找，ThreadLocal使用的开放寻址。
     *                     i = nextIndex(i, len);
     *                 e = tab[i];
     *             }
     *             return null;
     *         }
     *
     *
     * 二、保存解析
     *
     * 主要步骤：
     * 【1】获取当前线程
     * 【2】获取线程中属性threadLocals
     * 【3】通过该map.set方法继续宁设置
     *      （1）通过threadLocal计算hash值
     *      （2）寻找该threadLocal对应的Entry
     *              a.使用hash直接找到
     *              b.开放寻址继续寻找
     *      （3）找到就更新值，未找到new Entry()
     * 【4】若未查找到进行初始化
     *
     *   public void set(T value) {
     *          //1、获取当前线程
     *         Thread t = Thread.currentThread();
     *          //2、获取内部类对象，ThreadLocalMap
     *         ThreadLocalMap map = getMap(t);
     *         if (map != null)
     *             map.set(this, value);
     *         else
     *             createMap(t, value);
     *     }
     *
     *
     *   private void set(ThreadLocal<?> key, Object value) {
     *
     *             // We don't use a fast path as with get() because it is at
     *             // least as common to use set() to create new entries as
     *             // it is to replace existing ones, in which case, a fast
     *             // path would fail more often than not.
     *
     *             Entry[] tab = table;
     *             int len = tab.length;
     *             int i = key.threadLocalHashCode & (len-1);
     *             //1、第一次寻址相当于第一次hash
     *             for (Entry e = tab[i];
     *             //2、当前entry == null直接跳出循环，创建新的entry
     *                  e != null;
     *                  //3、若上一次 k!=key,则往下一个循环调用
     *                  e = tab[i = nextIndex(i, len)]) {
     *                 ThreadLocal<?> k = e.get();
     *
     *                 if (k == key) {
     *                     e.value = value;
     *                     return;
     *                 }
     *
     *                 if (k == null) {
     *                     replaceStaleEntry(key, value, i);
     *                     return;
     *                 }
     *             }
     *
     *             tab[i] = new Entry(key, value);
     *             int sz = ++size;
     *             if (!cleanSomeSlots(i, sz) && sz >= threshold)
     *                 rehash();
     *         }
     *
     *
     * 三、ThreadLocalMap解析
     *  static class ThreadLocalMap {
     *      //使用弱应用来应用threadLocal，如果没有强引用指向
     *      //该threadloacl，在下一次gc时就会将threadlocal进行回收
     *      //不然会导致内存泄漏的问题
     *      static class Entry extends WeakReference<ThreadLocal<?>> {
     *          Object value;
     *          Entry(ThreadLocal<?> k, Object v) {
     *              super(k);
     *              value = v;
     *              }
     *        }
     *      private static final int INITIAL_CAPACITY = 16;
     *      private Entry[] table;
     *      private int size = 0;
     *      private int threshold;
     *      }
     *
     */
}
