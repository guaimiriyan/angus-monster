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

    public static void main(String[] args) {
        int a = 0<<29;
        System.out.println(Integer.toBinaryString(a));
    }

    /**
     * 解析threadLocal源码解析
     *  https://blog.csdn.net/wanghao112956/article/details/102678591
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
     *                 //此时说明threadlocal已经被回收了，需要将其value也会受并赋值
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
     *         //todo 找到可以替换的entry往前挪动
     *         private void replaceStaleEntry(ThreadLocal<?> key, Object value,
     *                                        int staleSlot) {
     *             Entry[] tab = table;
     *             int len = tab.length;
     *             Entry e;
     *
     *             //这里采用的是从当前的staleSlot 位置向前面遍历，i--
     *             //这样的话是为了把前面所有的的已经被垃圾回收的也一起释放空间出来
     *             //（注意这里只是key 被回收，value还没被回收，entry更加没回收，所以需要让他们回收），
     *             //同时也避免这样存在很多过期的对象的占用,导致这个时候刚好来了一个新的元素达到阀值而触发一次新的rehash
     *             int slotToExpunge = staleSlot;
     *             for (int i = prevIndex(staleSlot, len);
     *                  (e = tab[i]) != null;
     *                  i = prevIndex(i, len))
     *                  //slotToExpunge 记录staleSlot左手边第一个空的entry 到staleSlot 之间key过期最小的index
     *                 if (e.get() == null)
     *                     slotToExpunge = i;
     *
     *             // 这个时候是从数组下标小的往下标大的方向遍历，i++，刚好跟上面相反。
     *             //这两个遍历就是为了在左边遇到的第一个空的entry到右边遇到的第一空的 entry之间查询所有过期的对象。
     *             //注意：在右边如果找到需要设置值的key（这个例子是key=15）相同的时候就开始清理，然后返回，不再继续遍历下去了
     *             for (int i = nextIndex(staleSlot, len);
     *                  (e = tab[i]) != null;
     *                  i = nextIndex(i, len)) {
     *                 ThreadLocal<?> k = e.get();
     *                 //说明之前已经存在相同的key,所以需要替换旧的值并且和前面那个过期的对象的进行交换位置，
     *                 //交换的目的下面会解释
     *                 if (k == key) {
     *                     e.value = value;
     *
     *                     tab[i] = tab[staleSlot];
     *                     tab[staleSlot] = e;
     *
     *                     //这里的意思就是前面的第一个for 循环(i--)往前查找的时候没有找到过期的，只有staleSlot
     *                     // 这个过期，由于前面过期的对象已经通过交换位置的方式放到index=i上了，
     *                     // 所以需要清理的位置是i,而不是传过来的staleSlot
     *                     if (slotToExpunge == staleSlot)
     *                         slotToExpunge = i;
     *                         //进行清理过期数据
     *                     cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
     *                     return;
     *                 }
     *
     *                 // 如果我们在第一个for 循环(i--) 向前遍历的时候没有找到任何过期的对象
     *                 // 那么我们需要把slotToExpunge 设置为向后遍历(i++) 的第一个过期对象的位置
     *                 // 因为如果整个数组都没有找到要设置的key 的时候，该key 会设置在该staleSlot的位置上
     *                 //如果数组中存在要设置的key,那么上面也会通过交换位置的时候把有效值移到staleSlot位置上
     *                 //综上所述，staleSlot位置上不管怎么样，存放的都是有效的值，所以不需要清理的
     *                 if (k == null && slotToExpunge == staleSlot)
     *                     slotToExpunge = i;
     *             }
     *
     *             // 如果key 在数组中没有存在，那么直接新建一个新的放进去就可以
     *             tab[staleSlot].value = null;
     *             tab[staleSlot] = new Entry(key, value);
     *
     *             // 如果有其他已经过期的对象，那么需要清理他
     *             if (slotToExpunge != staleSlot)
     *                 cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
     *         }
     *
     *         //todo 批量的去去除被回收的entry
     *         private int expungeStaleEntry(int staleSlot) {
     *         Entry[] tab = table;
     *         int len = tab.length;
     *
     *         tab[staleSlot].value = null;
     *         tab[staleSlot] = null;
     *         size--;
     *         Entry e;
     *         int i;
     *         for (i = nextIndex(staleSlot, len);
     *              (e = tab[i]) != null;
     *              i = nextIndex(i, len)) {
     *             ThreadLocal<?> k = e.get();
     *             if (k == null) {
     *             //这里设置为null ,方便让GC 回收
     *                 e.value = null;
     *                 tab[i] = null;
     *                 size--;
     *             } else {
     *             //这里主要的作用是由于采用了开放地址法，所以删除的元素是多个冲突元素中的一个，需要对后面的元素作
     *             //处理，可以简单理解就是让后面的元素往前面移动
     *             //为什么要这样做呢？主要是开放地址寻找元素的时候，遇到null 就停止寻找了，你前面k==null
     *             //的时候已经设置entry为null了，不移动的话，那么后面的元素就永远访问不了了，下面会画图进行解释说明
     *
     *                 int h = k.threadLocalHashCode & (len - 1);
     *                 //他们不相等，说明是经过hash 是有冲突的
     *                 if (h != i) {
     *                     tab[i] = null;
     *
     *                     while (tab[h] != null)
     *                         h = nextIndex(h, len);
     *                     tab[h] = e;
     *                 }
     *             }
     *         }
     *         return i;
     *     }
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
