package domain;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021年04月16日 09:46:00
 */
public class User implements Serializable {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
