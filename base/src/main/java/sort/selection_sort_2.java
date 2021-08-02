package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName selection_sort_2.java
 * @Description 选择排序一次循环同时找到最大值和最小值
 * @createTime 2021年08月02日 14:44:00
 */
public class selection_sort_2 {
    public static void main(String[] args) {
        //获取随机的数组列表
        final int[] datas = RandomUtil.getRandomIntArray(12, 1000);
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
        System.out.println("");
        //设置主要的参数
        for (int start = 0; start < (datas.length/2);start++) {
            int minIndex = start;
            int maxIndex = start;
            for (int inner = start;inner<datas.length-start-1;inner++){
                if (datas[minIndex]>datas[inner+1]){
                    minIndex = inner+1;
                }
                if (datas[maxIndex]<datas[inner+1]){
                    maxIndex = inner+1;
                }

            }
            //最后进行替换
            if (minIndex!=start){
                int temp = datas[start];
                datas[start] = datas[minIndex];
                datas[minIndex] = temp;
            }
            if (maxIndex!=(datas.length-start-1)){
                int temp = datas[datas.length-start-1];
                datas[datas.length-start-1] = datas[maxIndex];
                datas[maxIndex] = temp;
            }
            System.out.print("第"+(start+1)+"次排序：");
            for (int i = 0; i < datas.length; i++) {
                System.out.print(datas[i]+",");
            }
            System.out.println("");
        }
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
