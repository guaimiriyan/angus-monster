package sort;

import java.util.Random;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RandomUtil.java
 * @Description 争对排序算法编写的随机数器
 * @createTime 2021年08月02日 11:24:00
 */
public class RandomUtil {

    /**
     * 返回数组随机数
     * @param size
     * @param max
     * @return
     */
    public static int[] getRandomIntArray(int size,int max){
        //初始化数组
        final int[] ints = new int[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
           ints[i] = random.nextInt(max);
        }
        return ints;
    }
}
