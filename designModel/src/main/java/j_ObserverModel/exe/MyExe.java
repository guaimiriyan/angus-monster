package j_ObserverModel.exe;

import j_ObserverModel.core.EvenListener;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName MyExe.java
 * @Description TODO
 * @createTime 2021年03月17日 15:23:00
 */
public class MyExe extends EvenListener {
    public void click(){
        System.out.println("================开始执行click方法===============");
        this.trigger(MyEventType.CLICK);
    }
    public void touch(){
        System.out.println("================开始执行touch方法===============");
        this.trigger(MyEventType.TOUCH);
    }
}
