package com.angus.basic.cor;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName item.java
 * @Description TODO
 * @createTime 2021年04月08日 11:26:00
 */
public class item {
    String name;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public item(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "item{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
