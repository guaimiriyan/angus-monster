package sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Radix_Sort.java
 * @Description 基数排序
 * @createTime 2021年08月04日 11:01:00
 */
public class Radix_Sort {
    public static void main(String[] args) {
        int max = 1000;
        final int[] datas = RandomUtil.getRandomIntArray(100000, max);
//        for (int i = 0; i < datas.length; i++) {
//            System.out.print(datas[i]+",");
//        }
//        System.out.println("");
        final long start = System.currentTimeMillis();
        //确定max的位数
        int bit = 1;
        while ((max-1)/10>0){
            max = (max-1)/10;
            bit++;
        }
        //进行基数统计遍历每一个
        int cs = 1;
        for (int i = 0; i < bit; i++) {
            Map<Integer, ArrayList<Integer>> temp = new HashMap<>();
            for (int j = 0; j < datas.length; j++) {
                final int num = (datas[j]/cs) % 10;
                final ArrayList<Integer> integers = temp.get(num);
                if (integers==null){
                    final ArrayList<Integer> objects = new ArrayList<>();
                    objects.add(datas[j]);
                    temp.put(num,objects);
                }else {
                    integers.add(datas[j]);
                }
            }
            cs *= 10;
            int index = 0;
            for (int i1 = 0; i1 < 10; i1++) {
                final ArrayList<Integer> integers = temp.get(i1);
                if (integers!=null){
                    for (Integer integer : integers) {
                        datas[index] = integer;
                        index++;
                    }
                }
            }
        }
        final long end = System.currentTimeMillis();
        System.out.println("基数排序消耗时间"+(end-start));
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
