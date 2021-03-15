package e_DelegateModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Manage.java
 * @Description TODO
 * @createTime 2021年03月15日 10:11:00
 */
public class Manage {
    private Map<String,people> Workers = new HashMap<>();

    Manage(){
        Workers.put("摸鱼",new WorkerA());
        Workers.put("干活",new WorkerB());
    }


    public void dosomething(String command) {
        Workers.get(command).dosomething();
    }
}
