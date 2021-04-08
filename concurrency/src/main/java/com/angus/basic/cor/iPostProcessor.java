package com.angus.basic.cor;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName iPostProcessor.java
 * @Description 责任链的顶层接口
 * @createTime 2021年04月08日 10:06:00
 */
public interface iPostProcessor {

    void putProcess() throws InterruptedException;
    void process(item item);
}
