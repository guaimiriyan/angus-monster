package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Merge_sort_2.java
 * @Description 归并排序的非递归版本
 * @createTime 2021年08月06日 15:06:00
 */
public class Merge_sort_2 {
    public static void main(String[] args) {
        //final int[] datas = RandomUtil.getRandomIntArray(100000, 1000);
        final int[] datas = new int[]{1,3,4,2,5};

//        for (int data : datas) {
//            System.out.print(data+",");
//        }
//        System.out.println("");
        final long start = System.currentTimeMillis();
        Integer num = 0;
        int size = 2;
        while (size<datas.length<<1){
            int fz = datas.length / size;
            if (datas.length%size>0) fz++;
            for (int i = 0; i < fz; i++) {
                int left = size*i;
                int right = left+size-1> datas.length-1?datas.length-1:left+size-1;
                merge(datas,left,right,size,num);
            }
            size<<=1;
        }
        final long end = System.currentTimeMillis();
        System.out.println("非递归归并排序消耗时间"+(end-start));
        for (int data : datas) {
            System.out.print(data+",");
        }
        System.out.println(num);
    }

    public static void merge(int[] datas,int left,int right,int size,Integer num){
        int length = right-left+1;
        int[] temp = new int[length];
        int p1 = left;
        int p2 = left+(size/2)>right?left+(length/2):left+(size/2);
        int p3 = p2;
//        System.out.println("p1:"+p1);
//        System.out.println("p2:"+p2);
        for (int i = 0; i < length; i++) {

            if (p1<p3&&(p2>right||datas[p1]<datas[p2])){
                temp[i] = datas[p1];
                if (p2<=right){
                    for (int j = p2;j<=right;j++){
                            System.out.println("左侧比右侧小的数:"+datas[p1]);
                        }
                }
                p1++;
            }else {
                temp[i] = datas[p2];
                p2++;
            }
        }
//        for (int i : temp) {
//            System.out.print(i+",");
//        }
        for (int i = 0; i < temp.length; i++) {
            datas[left+i]=temp[i];
        }
//        System.out.println("");
    }
}
