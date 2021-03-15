package e_DelegateModel;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName delegateTest.java
 * @Description TODO
 * @createTime 2021年03月15日 10:22:00
 */

/**
 *如果将其模拟在真是的业务代码中
 * 1、client可以充当boss的角色
 * 2、***Delegate类充当的是mannage的角色
 * 3、lookup类是就是任务分发的逻辑，在这个例子中使用的是map
 * 4、impl则是真正做业务的类，也就是worker的角色
 */
public class delegateTest {
    public static void main(String[] args) {
        final Boss boss = new Boss();
        boss.dosomething("摸鱼",new Manage());
    }
}
