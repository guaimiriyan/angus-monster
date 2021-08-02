package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Selection_sort.java
 * @Description 选择排序代码实现|时间复杂度O(n^2)
 * @createTime 2021年08月02日 11:23:00
 */
public class Selection_sort {
    public static void main(String[] args) {
        //获取随机的数组列表
        final int[] datas = RandomUtil.getRandomIntArray(10, 1000);
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
        System.out.println("");
        //设置主要的参数
        for (int start = 0; start < datas.length-1;start++) {
            int minIndex = start;
            for (int inner = start;inner<datas.length-1;inner++){
             if (datas[minIndex]>datas[inner+1]){
                 minIndex = inner+1;
             }
            }
            //最后进行替换
            if (minIndex!=start){
                int temp = datas[start];
                datas[start] = datas[minIndex];
                datas[minIndex] = temp;
            }
        }
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
