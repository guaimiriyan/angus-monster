### java并发实战
#### 并发存在的问题
*** 
* 安全性
* 活跃性
* 并发性能

#### 线程安全性
&nbsp;&nbsp;线程安全性的主要目标是解决对共享和可变进行访问，共享主要是确定变量可以由多个线程同时访问，
可变就是确定在生命周期内可以发生变化。
保证变量同步的方式：
* 不在多个线程中共享该变量
* 使用不可变的类
* 使用同步的手段进行控制

#### 原子性
&nbsp;&nbsp;原子性是
##### 竞态条件问题
* 先检查后执行问题（检查的对象就是竞争条件）
```java
/**
 * 该instance==null就为检查对象，但是他是一个竞争条件，
 * 当在检查之后他的值会瞬息万变。
 */
public class lazyInit{
    private Object instance = null;
    public Object getInstance(){
        if (instance == null){
            instance = new Object();
        }
        return instance;
    } 
}

```
* 读取-修改-写入问题(修改的对象是竞态条件)
##### 复合操作问题
&nbsp;&nbsp;保证单个竞态条件，也就是从无状态的类到只有一个状态变量下可以单纯的使用线程安全的类就是进行安全的保证。
但是如果 是如果是添加了多个竞态变量又该如何让解决呢？根据代码内的注释告诉我们必须保证所有状态同时更新
的原子性。

```java
/**
 * 分析面程序进行分析
 */
@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet implements Servlet {
    private final AtomicReference<BigInteger> lastNumber
            = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors
            = new AtomicReference<BigInteger[]>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get()))
            encodeIntoResponse(resp, lastFactors.get());
        else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            //这两步没有原子性，会导致一个更新另一个还未更新就被获取到
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }
}
```

#### 需要进行锁来保证
```java

@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;

    public synchronized void service(ServletRequest req,
                                     ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber))
            encodeIntoResponse(resp, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }
}
```
##### 内置锁
##### 重入的作用