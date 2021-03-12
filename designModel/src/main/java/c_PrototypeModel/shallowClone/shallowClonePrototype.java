package c_PrototypeModel.shallowClone;

import c_PrototypeModel.prototype.Home;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName shallowClonePrototype.java
 * @Description TODO
 * @createTime 2021年03月12日 11:23:00
 */
public class shallowClonePrototype {

    public static void main(String[] args) {
        chinese chinese = new chinese("angus",10,new Home("cangshengdasha",10000));
        chinese clone = chinese.clone();
        System.out.println(clone.getHome() == chinese.getHome());
    }
}
