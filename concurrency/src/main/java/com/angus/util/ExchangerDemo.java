package com.angus.util;

import java.util.concurrent.Exchanger;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ExchangerDemo.java
 * @Description 简单使用Exchanger
 * @createTime 2021年03月01日 21:12:00
 */
public class ExchangerDemo {


    /**
     *
     * 目前对其不求详细内容，知道其如何使用
     *
     *
     */

    static class User{
        private String name;
        User(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Exchanger<User> userExchanger = new Exchanger<>();
        new Thread(()->{
            User angus1 = new User("angus1");
            System.out.println("A交换之前："+angus1);
            try {
                User exchange = userExchanger.exchange(angus1);
                System.out.println("A交换之后："+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        Thread.sleep(10000);

        User angus2 = new User("angus2");
        System.out.println("B交换之前："+angus2);
        User exchange = userExchanger.exchange(angus2);
        System.out.println("B交换之侯："+exchange);

    }
}
