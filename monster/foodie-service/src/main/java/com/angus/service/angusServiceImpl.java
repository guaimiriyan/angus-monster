package com.angus.service;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName angusServiceImpl.java
 * @Description TODO
 * @createTime 2021年02月19日 23:27:00
 */
public class angusServiceImpl implements angusService{
    @Override
    /**
     * @title talk
     * @description
     * @author admin []
     * @updateTime 2021/2/20 [] void
     * @throws 用于jdk代理使用
     */
    public void talk() {
        System.out.println("I'am angus");
    }

    public  void selfTalk(){
        System.out.println("cglib use !");
    }
}
