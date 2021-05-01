package com.controller;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Mag.java
 * @Description TODO
 * @createTime 2021年04月27日 22:27:00
 */
public class Mag {
    String name;
    String age;

    public Mag(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Mag{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
