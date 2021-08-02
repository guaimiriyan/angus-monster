package sort;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName insert_sort.java
 * @Description 插入排序
 * @createTime 2021年08月02日 16:23:00
 */
public class insert_sort {
    public static void main(String[] args) {
        final int[] datas = RandomUtil.getRandomIntArray(10, 100);
        for (int j = 1; j < datas.length; j++) {
            for (int i = j; i >0 ; i--) {
                //进行交换
                if (datas[i]<datas[i-1]){
                    int temp = datas[i];
                    datas[i] = datas[i-1];
                    datas[i-1] = temp;
                }else {
                    break;
                }
            }
        }
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i]+",");
        }
    }
}
