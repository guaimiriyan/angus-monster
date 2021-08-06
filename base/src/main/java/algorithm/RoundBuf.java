package algorithm;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RoundBuf.java
 * @Description 使用数组实现环状队列
 * @createTime 2021年08月05日 16:19:00
 */
public class RoundBuf {
    int head;
    int tail;
    int size;
    int[] buf;

   RoundBuf(int size){
       buf = new int[size];
       head = 0;
       tail = 0;
       size = 0;
   }

   public void pop(int num){
//       if (size)
//       buf[tail] = num;
//       tail =
   }

   public int push(){
       return 0;
   }



}
