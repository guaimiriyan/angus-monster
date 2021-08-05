package algorithm;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName FindBitOneNum.java
 * @Description 查找出一个数二进制1的个数
 * @createTime 2021年08月05日 10:47:00
 */
public class FindBitOneNum {
    public static void main(String[] args) {
        int num = 125;
        int count = 0;
        System.out.println(Integer.toBinaryString(num));
        while (num!=0){
            num^=(num&(~num+1));
            count ++;
        }
        System.out.println(count);
    }

}
