package e_DelegateModel;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Worker.java
 * @Description TODO
 * @createTime 2021年03月15日 10:15:00
 */
public class WorkerA implements people{
    @Override
    public void dosomething() {
        System.out.println("我擅长摸鱼，我上班就是为了摸鱼！");
    }
}
