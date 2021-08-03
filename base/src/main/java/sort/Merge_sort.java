package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Merge_sort.java
 * @Description 归并排序
 * @createTime 2021年08月03日 09:58:00
 */
public class Merge_sort {
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(100000, 1000);
//        for (int i = 0; i < datas.length; i++) {
//            System.out.print(datas[i]+",");
//        }
//        System.out.println("");
        //使用递归的方式实现归并排序算法，使用二分法将所有的数据汇总上去
        final long start = System.currentTimeMillis();
        int[] sorted = mergeSort(datas,0,datas.length-1);
        final long end = System.currentTimeMillis();
        System.out.println("归并排序消耗时间"+(end-start));
         for (int i = 0; i < sorted.length; i++) {
            System.out.print(sorted[i]+",");
        }
    }

    private static int[] mergeSort(int[] datas,int start,int end) {
        //如果可以再分就是用进行递归
       int lenth =  end-start+1;
       if (lenth==1){
           return new int[]{datas[end]};
       }else {
           //计算end和start中间值进行分割，分而治之
            int mid = (end+start) >> 1;
            int[] merge1 = mergeSort(datas, start, mid);
//           System.out.print("merge1:");
//           for (int i = 0; i < merge1.length; i++) {
//               System.out.print(merge1[i]+",");
//           }
//           System.out.println("");
            int[] merge2 = mergeSort(datas, mid+1, end);
//           System.out.print("merge2:");
//           for (int i = 0; i < merge2.length; i++) {
//               System.out.print(merge2[i]+",");
//           }
//           System.out.println("");
            //归并返回
           int merge1Index = 0;
           int merge2Index = 0;
           final int[] mergeData = new int[lenth];
           for (int i = 0; i < lenth; i++) {
               if (merge1Index<merge1.length&&(merge2Index==merge2.length||merge1[merge1Index]<merge2[merge2Index])){
                   mergeData[i] = merge1[merge1Index];
                   merge1Index++;
               }else {
                   mergeData[i] =merge2[merge2Index];
                   merge2Index++;
               }
           }
           return mergeData;
       }
    }
}
