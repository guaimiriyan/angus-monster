package algorithm;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName TwoOddNumber.java
 * @Description 一个数组里有只有两个数出现奇数次如何找到这两个数
 * @createTime 2021年08月05日 09:58:00
 */
public class TwoOddNumber {
    public static void main(String[] args) {
        int[] datas = new int[]{2,1,4,6,8,10,12,14,17,2,4,6,8,10,12,14};
        /**
         * 1、将所有数字抑或起来可以得到a^b
         */
        int eor = 0;
        for (int i = 0; i < datas.length; i++) {
            eor^=datas[i];
        }
        System.out.println(Integer.toBinaryString(eor));
        System.out.println("取反："+Integer.toBinaryString((~eor)+1));
        //2、找到最右侧为1的值
        final int eor2 = eor&((~eor)+1);
        int eor3 = 0;
        System.out.println(Integer.toBinaryString(eor2));
        for (int i = 0; i < datas.length; i++) {
            final int i1 = datas[i] & eor2;
            System.out.println(i1);
            if (i1 == eor2) eor3^=datas[i];
        }
        System.out.println(eor3);
        //
        System.out.println(eor^eor3);

    }
}
