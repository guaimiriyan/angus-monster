package com.angus.basic.cor;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName needPostProcessor.java
 * @Description TODO
 * @createTime 2021年04月08日 10:08:00
 */
public class needPostProcessor  implements iPostProcessor,Runnable{
    iPostProcessor nextPostProcessor;

    LinkedBlockingQueue<item> postProcessors = new LinkedBlockingQueue<>();

    /**
     * 需要创建一个队列将需要存取的任务堆积起来让下一个责任链可以使用
     */

    public needPostProcessor(iPostProcessor nextPostProcessor) {
        this.nextPostProcessor = nextPostProcessor;
    }

    @Override
    public void putProcess() throws InterruptedException {
    }

    @Override
    public void process(item item) {
        //这里是真正处理业务的过程
        postProcessors.add(item);
    }

    @Override
    public void run() {
        while (true){
            /**
             * 在这里不断从责任链中的队列中获取执行
             */
            try {
                final item take = postProcessors.take();
                System.out.println("需求处理过程"+take);
                Thread.sleep(1000);
                nextPostProcessor.process(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
