package springStart;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectDemo {

    AspectDemo(){
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
    /**
     * execution(public * *(..))任意返回值，任意方法名，任意参数，只要是public就可以
     * execution(* set*(..))：以set开头的方法
     * execution(* com.xyz.service.AccountService.*(..))AccountService的所有方法
     * execution(* com.xyz.service.*.*(..)) service包下面的所有类的所有方法
     * execution(* com.xyz.service..*.*(..)) service包以及子包下面的所有类的所有方法
     * within(com.xyz.service.*)：service包里面
     * within(com.xyz.service..*)：service包以及子包里面
     * this(com.xyz.service.AccountService)：实现了AccountService的代理对象
     * target(com.xyz.service.AccountService)：实现了AccountService的目标对象
     * args(java.io.Serializable)：方法只有一个参数，并且实现了Serializable
     * args(java.io.Serializable, java.lang.String,..)：方法的前两个参数是Serializable和String
     * @target(org.springframework.transaction.annotation.Transactional)目标对象上有Transactional注解
     * @within(org.springframework.transaction.annotation.Transactional)目标对象上有Transactional注解
     * @annotation(org.springframework.transaction.annotation.Transactional)方法上定义了Transactional注解
     * @args(com.xyz.security.Classified)：方法只有一个参数，并且参数上有Classified注解
     * bean(tradeService)：名字叫tradeService的bean
     * bean(*Service)：bean的名字以Service结尾
     */
    @Pointcut("")
    public void execut(){}

    @Before("execut()")
    public void before(){
        System.out.println("在这之前！");
    }

    @After("execut()")
    public void after(){
        System.out.println("在这之后！");
    }

    @Around("execut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕之前");
        Object[] args = pjp.getArgs();
        Object proceed = pjp.proceed(args);
        System.out.println("环绕之后");
        return proceed;
    }
}
