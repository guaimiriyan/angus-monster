package com.angus.basic;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ForkJoinDemo.java
 * @Description 初次使用forkJoin这种方式进行任务分解
 * @createTime 2021年03月01日 14:17:00
 */
public class ForkJoinDemo extends RecursiveTask<Integer> {

    /**
     * ForKJoin原理解析【暂留】
     *
     */
    static int MaxNum = 100;

    private int start;
    private int end;

    ForkJoinDemo(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int total = 0;
       if ((end - start)+1<=MaxNum){
           System.out.println("计算数据start:"+start+",end:"+end);
           for (int i = start; i <= end; i++) {
               total += i;
           }
           return total;
       }else {
           ForkJoinDemo left = new ForkJoinDemo(start, ((end+start) / 2));
           ForkJoinDemo right = new ForkJoinDemo(((end+start)/2+1), end);
           left.fork();
           right.fork();
           return left.join()+ right.join();
       }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int total = 0;
        for (int i = 0; i < 4001; i++) {
            total += i ;
        }
        System.out.println(total);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo forkJoinDemo = new ForkJoinDemo(0, 4000);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(forkJoinDemo);
        if ( forkJoinDemo.isCompletedAbnormally()){
            System.out.println(forkJoinDemo.getException());
        }

        Integer integer = submit.get();
        System.out.println(integer);
        //Thread.sleep(100000);
    }
}
