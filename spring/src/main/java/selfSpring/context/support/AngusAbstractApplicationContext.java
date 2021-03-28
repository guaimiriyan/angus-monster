package selfSpring.context.support;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusAbstractApplicationContext.java
 * @Description 该类为简单的模拟使用该类的refresh方法进行对对容器的初始化
 * @createTime 2021年03月28日 20:32:00
 */
public abstract class AngusAbstractApplicationContext {

    /** Synchronization monitor for the "refresh" and "destroy" */
    private final Object startupShutdownMonitor = new Object();


    /** System time in milliseconds when this context started */
    private long startupDate;

    /** Flag that indicates whether this context is currently active */
    private final AtomicBoolean active = new AtomicBoolean();

    /** Flag that indicates whether this context has been closed already */
    private final AtomicBoolean closed = new AtomicBoolean();

    /**
     * 使用该方法对容器进行初始化，主要是使用模板模式。
     */
    public void refresh(){
        synchronized (this.startupShutdownMonitor){
            prepareRefresh();
            obtainFreshBeanFactory();
        }
    }

    /**
     *
     */
    protected  void obtainFreshBeanFactory(){
        refreshBeanFactory();
    }

    /**
     * 给ApplicationContext一个简单的信息及逆行初始化准备
     */
    protected  void prepareRefresh(){
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);
    }

    /**
     * 这里属于委派模式将加载上下文的工作交给子类去做
     */
    protected abstract void refreshBeanFactory();


    protected abstract void loadBeanDefinitions(AngusDefaultListableBeanFactory beanFactory);
}
