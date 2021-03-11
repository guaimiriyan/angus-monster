package com.angus.basic;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName finalTest.java
 * @Description 测试final的一些功能
 * @createTime 2021年03月05日 10:11:00
 */
public class finalTest {
}

/**
 * 为什么在private 域中还要使用final，
 * 如果private 可以通过方法将对象中发布出去所以并不是完全安全的
 * 只有通过final来修饰通过代码规定才能真正解决。
 */
class finalObj{
    private final Integer value;

    finalObj(Integer value) {
        this.value = value;
    }


}
