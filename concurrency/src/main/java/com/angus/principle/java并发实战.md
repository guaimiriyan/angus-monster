### java并发实战
#### 并发存在的问题
*** 
* 安全性
* 活跃性
* 并发性能

### 线程安全性
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

###对象的共享
#### 可见性问题（happen before）
###### 锁
###### volatile

#### 对象的发布与逸出
解决对象发布导致的问题方案如下：
#### 线程封闭
1. Ad_hoc
2. 封闭栈
3. ThreadLocal
#### 不变性
1. 使用final字段描述类域
2. 且用volatile进行发布
#### 安全发布
1. 使用静态例如饿汉式的单例
2. 使用final域对其进行对象引用进行保存
3. 将对象引用保存到volatile与AtomicReference
4. 使用锁或者保护对象引用

###对象组合
#### 实例封闭
主要是用java监视器模式，final+lock
#### 委托
1. 委托给线程安全的类+不可变类(一个)
2. 多个变量就需要通过同步的方式进行
####在线程安全的类中添加方法
1. 修改类的源码，以添加新的原子操作
2. 继承该线程安全类，并添加原子操作
3. 使用客户端加锁方式
4. 使用组合方式 （推荐）


