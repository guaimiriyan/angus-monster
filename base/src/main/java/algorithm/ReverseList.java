package algorithm;

import sort.RandomUtil;

import java.util.Random;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ReverseList.java
 * @Description 实现一个单向链表进行进行反转链表操作
 * @createTime 2021年08月05日 11:34:00
 */
public class ReverseList {

    public class node{
        node next;
        int value;
        node(int value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "node{" +
                    "next=" + next +
                    ", value=" + value +
                    '}';
        }
    }

    public  node buildRandomList(int size,int max){
        final Random random = new Random();
        node head = null;
        node next = null;
        for (int i = 0; i < size; i++) {
            final node node = new node(random.nextInt(max));
            if (i==0){
                head=node;
                next = node;
            }else {
                next.next = node;
                next = node;
            }

        }
        return head;
    }

    public void readAll(node head){
        node next = head;
        while (next!=null){
            System.out.print(next.value+",");
            next = next.next;
        }
        System.out.println("结束");
    }

    public node reverse(node head){
        node pre = head.next;
        head.next = null;
        while (pre!=null){
            node temp = pre.next;
            pre.next = head;
            head = pre;
            pre = temp;
        }
        return head;
    }

    public static void main(String[] args) {
        final ReverseList reverseList = new ReverseList();
        node head = reverseList.buildRandomList(10, 100);
        reverseList.readAll(head);
        head = reverseList.reverse(head);
        System.out.println(head);
        reverseList.readAll(head);

    }

}
