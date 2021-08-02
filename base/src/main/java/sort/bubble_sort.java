package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName bubble_sort.java
 * @Description 冒泡排序
 * @createTime 2021年08月02日 15:53:00
 */
public class bubble_sort {

    /**
     * 冒泡排序的优化方案
     * 一、
     */
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(10, 100);
        for (int j = 0; j < datas.length-1; j++) {
            for (int i = 0; i < datas.length-j-1; i++) {
                //进行交换
                if (datas[i]>datas[i+1]){
                    int temp = datas[i];
                    datas[i] = datas[i+1];
                    datas[i+1] = temp;
                }
            }
        }
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
