package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Shell_Sort.java
 * @Description 希尔排序
 * @createTime 2021年08月02日 17:09:00
 */
public class Shell_Sort {
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(100, 100);
        //希尔排序最大的，开始将间隔设置位4
        //每次讲gap除以二，也就是减少到一半
        for (int gap = 4;gap>=1;gap = gap/2){
            //查看这个间隔能分为多少组，此处为25组
            System.out.println(gap+":");
            for (int i = 0;i< gap;i++){
                for (int j = i;j<datas.length;j+=gap){
                    for (int k = j;k>=gap;k-=gap){
                        if (datas[k]<datas[k-gap]){
                            int temp = datas[k];
                            datas[k] = datas[k-gap];
                            datas[k-gap] = temp;
                        }
                    }
                }
            }
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
