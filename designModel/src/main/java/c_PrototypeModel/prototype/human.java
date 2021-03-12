package c_PrototypeModel.prototype;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName human.java
 * @Description TODO
 * @createTime 2021年03月12日 09:56:00
 */
public class human implements Serializable,Cloneable {

    private String name;
    private int age;
    private Home home;

    public human(String name, int age, Home home) {
        this.name = name;
        this.age = age;
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}
