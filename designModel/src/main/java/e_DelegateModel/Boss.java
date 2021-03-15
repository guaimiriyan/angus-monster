package e_DelegateModel;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Boss.java
 * @Description TODO
 * @createTime 2021年03月15日 10:09:00
 */
public class Boss {


    public void dosomething(String command,Manage manage) {
        manage.dosomething(command);
    }
}
