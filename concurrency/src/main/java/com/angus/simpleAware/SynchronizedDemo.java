package com.angus.simpleAware;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName SynchronizedDemo.java
 * @Description 实验synchronized的作用
 * @createTime 2021年02月25日 09:22:00
 */
public class SynchronizedDemo {
    //代表为静态变量
    public static Object obj = new Object();
    //代表动态变量
    public  Object obj1 = new Object();

    /**
     * 一、在非静态方法使用结果
     * 1、当synchronized修饰非静态方法时他上锁的对象就是跟synchronized(this),是一样的效果
     *    锁住的都是当前的对象。
     * 2、当synchronized使用对象内非静态属性时，与synchronized(this)是不会互斥的。
     *
     * 二、在静态方法中使用
     * 1、当synchronized修饰静态方法时他上锁的对象就是跟synchronized(xxx.class),是一样的效果
     *    锁住的都是当前的对象。
     * 2、当synchronized使用对象内静态属性时，与synchronized(static field)是不会互斥的。
     *
     * 三、最终
     * 所以判断synchronized的锁定规则就只有三条
     * （1）在非静态方法上和Synchronized(this)一致
     * （2）在静态方法上和Synchronized(当前类.class)一致
     * （3）在其他时候锁住的都是括号里的对象
     *
     * 判断：最后只需要判断是不是竞争的是否是同一个对象，是则互斥
     *
     * 角度二：
     * 从synchronized另一个优化我们也可以。
     * 如果每次访问同步代码块都有一个获取锁和释放锁的过程是十分消耗资源的
     * java 1.6之后提出了所得升级
     * 升级方向 偏向锁-》轻量锁 -》重量锁
     *
     * 对象头和MarkWord状态变化
     *  32/64bit  Mark Word
     *  32/64bit  存储对象类型指针（指方法区中类数据）
     *  32/32bit  如果为数据则需要该空间存数组长度
     *
     *  Mark Word（32bit）
     *  无锁状态 -》hashcode(25bit)|分带年龄（4bit）|是否偏向（1bit）|锁标志（2bit）【01】
     *  偏向锁-》threadId(23bit)|Epoch(2bit)|分带年龄（4bit）|是否偏向（1bit）|锁标志（2bit）【01】
     *  轻量级锁-》线程栈中锁记录指针(25bit)|分带年龄（4bit）|是否偏向（1bit）|锁标志（2bit）【00】
     *  重量级锁-》重量级锁指针(25bit)|分带年龄（4bit）|是否偏向（1bit）|锁标志（2bit）【10】
     *  GC标志-》空(30bit)|锁标志(2bit)【11】
     *
     * 偏向锁：
     * 1、当线程进入方法提之前回去获取锁，如果此时标志为【01】
     * 2、判断偏向标志是否为1，如果是则判断线程Id是否为该线程
     * 3、如果是该线程，就直接运行，如果不是则可以改写Markword
     * 4、修改成功则重新偏向，如果失败说明有线程持有偏向锁
     * 5、等待持有偏向锁的线程进入安全点，检查是否在同步块内
     * 6、如果在则继续执行，并把warkWord清空升级为轻量级锁。如果不在则重新偏向
     *
     * 轻量级锁：
     * *由于jvm-XX:-UseBiaseLocking = false可以关闭偏向锁，可能是升级得来，也可能是初始就是轻量级锁
     * 1、在需要获取该轻量级锁的时候将对象里的Mark word复制到当前栈中锁记录，然后25bit改为指向所记录指针
     * 2、如果修改成功则执行改变为轻量级锁，执行同步块。
     * 3、当其他线程修改该Mark word失败时，就需要自旋等待。
     *
     *
     *
     * 重量级锁：
     * 1、轻量级锁自旋达到一定次数之后就会变为重量级锁，其他线程会导致阻塞。
     *
     * 结论：永远都是通过对象头在进行操作，锁住的只是单单的某个对象。
     *
     *四、synchronzied的字节码文件
     *
     * public void methodObj();
     *     Code:
     *        0: aload_0
     *        1: getfield      #3                  // Field obj1:Ljava/lang/Object;
     *        4: dup
     *        5: astore_1
     *        6: monitorenter
     *        7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *       10: ldc           #12                 // String synchronized use in method,with obj's params!
     *       12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *       15: ldc2_w        #7                  // long 10000l
     *       18: invokestatic  #9                  // Method java/lang/Thread.sleep:(J)V
     *       21: goto          29
     *       24: astore_2
     *       25: aload_2
     *       26: invokevirtual #11                 // Method java/lang/InterruptedException.printStackTrace:()V
     *       29: aload_1
     *       30: monitorexit
     *       31: goto          39
     *       34: astore_3
     *       35: aload_1
     *       36: monitorexit
     *       37: aload_3
     *       38: athrow
     *       39: return
     *
     *  其中包括monitorenter和 monitorexit来控制同步块进行控制
     *
     * */

    public  synchronized void methodNull()  {
        System.out.println("synchronized use in method with out param!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void methodObj() {
        synchronized (obj1){
            System.out.println("synchronized use in method,with obj's params!");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void methodThis()  {
        synchronized (this){
            System.out.println("synchronized use in method,params is this");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void methodClass()  {
        synchronized (SynchronizedDemo.class){
            System.out.println("synchronized use in method,params is class");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static  synchronized void methodStatic()  {
            System.out.println("synchronized use in static method");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public static  void methodStaticField()  {
        synchronized (obj){
            System.out.println("synchronized use in static method，with static param");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static  void methodStaticClass()  {
        synchronized (SynchronizedDemo.class){
            System.out.println("synchronized use in static method，with class");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        //编写三个线程去执行他们
        Thread thread = new Thread(() -> {
            synchronizedDemo.methodNull();
        });
//        Thread thread1 = new Thread(() -> {
//            synchronizedDemo.methodObj();
//        });
//        Thread thread2 = new Thread(() -> {
//           new SynchronizedDemo().methodThis();
//        });
//        Thread thread3 = new Thread(() -> {
//            methodStatic();
//        });
//        Thread thread4 = new Thread(() -> {
//           methodStaticField();
//        });
//        Thread thread5 = new Thread(() -> {
//           methodStaticClass();
//        });
//        Thread thread6 = new Thread(() -> {
//            synchronizedDemo.methodClass();
//        });

        thread.start();
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        //thread5.start();
//        thread6.start();
        long use = System.currentTimeMillis() - start;
        System.out.println("一共使用时间："+use);
    }
}
