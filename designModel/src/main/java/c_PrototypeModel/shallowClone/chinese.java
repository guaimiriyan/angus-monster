package c_PrototypeModel.shallowClone;

import c_PrototypeModel.prototype.Home;
import c_PrototypeModel.prototype.human;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName chinese.java
 * @Description TODO
 * @createTime 2021年03月12日 11:28:00
 */
public class chinese extends human {

    public chinese(String name, int age, Home home) {
        super(name, age, home);
    }

    public chinese clone(){
        try {
            return (chinese) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
