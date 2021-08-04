package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Quick_sort.java
 * @Description 快速排序
 * @createTime 2021年08月03日 15:09:00
 */
public class Quick_sort {
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(100000, 1000);
//        for (int i = 0; i < datas.length; i++) {
//            System.out.print(datas[i]+",");
//        }
        final long start = System.currentTimeMillis();
        quickSort(datas,0,datas.length-1);
        final long end = System.currentTimeMillis();
        System.out.println("快速排序消耗时间"+(end-start));
         for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }

    private static void quickSort(int[] datas,int start,int end) {
        //每次都将第一个数作为基准数据
        if (start == end) return;
        int temp = datas[start];
//        System.out.println("标志位数据为："+temp);
        //进行分组
        int left = start+1;
        int right = end;
        while (left<right){
            if (datas[right]<datas[left]&&datas[right]<temp&&datas[left]>temp){
                int chg = datas[left];
                datas[left] = datas[right];
                datas[right] = chg;
                right--;
                left++;

            }else {
                if (datas[right]>=temp)right--;
                if (datas[left]<=temp) left++;
            }
        }
        if (datas[left]>temp){
            int chg = datas[start];
            datas[start] = datas[left-1];
            datas[left-1] = chg;
        }else {
            int chg = datas[start];
            datas[start] = datas[left];
            datas[left] = chg;
        }

//        int chg = datas[start];
//        datas[start] = datas[left];
//        datas[left] = chg;

        //此时进行设置
//        System.out.println("此时的left:"+left);
//        System.out.println("此时的right:"+right);
//        System.out.println("此时的start:"+start);
//        System.out.println("此时的end:"+end);

//        for (int i = 0; i < datas.length; i++) {
//            System.out.print(datas[i]+",");
//        }
//        System.out.println("");
        quickSort(datas,start,left-1);
        quickSort(datas,right,end);
    }
}
