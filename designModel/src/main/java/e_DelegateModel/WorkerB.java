package e_DelegateModel;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName WorkerB.java
 * @Description TODO
 * @createTime 2021年03月15日 10:16:00
 */
public class WorkerB implements people{
    @Override
    public void dosomething() {
        System.out.println("我擅长干活,我的任务就是为了干活！");
    }
}
