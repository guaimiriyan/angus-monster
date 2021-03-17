package j_ObserverModel.exe;

import j_ObserverModel.core.Event;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName observerTest.java
 * @Description TODO
 * @createTime 2021年03月17日 15:58:00
 */
public class observerTest {
    public static void main(String[] args) {
        final MyExe myExe = new MyExe();
        /**
         *
         * 使用匿名内部类时需要反射的时候会出现如下问题
         * java.lang.IllegalAccessException: Class j_ObserverModel.core.EvenListener can not access a member
         * of class j_ObserverModel.exe.observerTest$1 with modifiers "public"
         *
         * 主要是匿名内部类时生成一个类继承使用的接口或者类，切修饰符未default，只有在同一包下的方法，
         * 在反射时可以使用super.class 或者使用 getsuperclass();
         *
         */
        myExe.addListener(MyEventType.CLICK, new MyCallBack() {
            @Override
            public void onClick(Event event) {
                System.out.println("点击事件已经触发：\n"+event);
            }


        });
        myExe.click();
    }
}
