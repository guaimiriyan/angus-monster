package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName insert_sort_2.java
 * @Description TODO
 * @createTime 2021年08月02日 16:35:00
 */
public class insert_sort_2 {
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(10, 100);
        for (int j = 1; j < datas.length; j++) {
            int temp = datas[j];
            for (int i = j; i >0 ; i--) {
                //进行交换
                if (datas[i]<datas[i-1]){
                    datas[i] = datas[i-1];
                }else {
                    datas[i] = temp;
                    break;
                }
            }
        }
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
