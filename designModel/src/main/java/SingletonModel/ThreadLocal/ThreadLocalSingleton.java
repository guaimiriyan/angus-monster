package SingletonModel.ThreadLocal;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ThreadLocalSingleton.java
 * @Description 基于threadlocal中来解决单例模式
 * @createTime 2021年03月12日 09:42:00
 */
public class ThreadLocalSingleton {

    private ThreadLocalSingleton(){}

    /**
     * threadLocal的使用方式可以到concurrent包下可以查看和学习。
     * 主要是是将单例做了线程封闭来进行数据的不可见性。
     */
    private static final ThreadLocal<ThreadLocalSingleton> threadContainer = new ThreadLocal<ThreadLocalSingleton>(){
        @Override
        protected ThreadLocalSingleton initialValue() {
            return new ThreadLocalSingleton();
        }
    };


    public static final ThreadLocalSingleton getThreadInstance(){
        return threadContainer.get();
    }
}
