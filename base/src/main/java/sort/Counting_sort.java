package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Counting_sort.java
 * @Description 计数排序
 * @createTime 2021年08月04日 10:24:00
 */
public class Counting_sort {
    public static void main(String[] args) {
        final int max = 1000;
        final int[] datas = RandomUtil.getRandomIntArray(100000, max);
        //由于最大的值为max，设置大小为1000的数组
        final long start = System.currentTimeMillis();
        int[] countArray = new int[max];
        for (int data : datas) {
            countArray[datas[data]]+=1;
        }
        //最后进行赋值
        int index = 0;
        for (int i = 0; i < countArray.length; i++) {
            final int count = countArray[i];
            for (int j = 0; j < count; j++) {
                datas[index] = i;
                index++;
            }
        }
        final long end = System.currentTimeMillis();
        System.out.println("计数排序消耗时间"+(end-start));
         for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
