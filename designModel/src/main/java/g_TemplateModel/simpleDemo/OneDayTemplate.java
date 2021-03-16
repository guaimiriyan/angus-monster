package g_TemplateModel.simpleDemo;

import java.util.Calendar;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName OneDayTemplate.java
 * @Description 每个人的一天
 * @createTime 2021年03月16日 11:26:00
 */
public abstract class OneDayTemplate {

    final void doSomething(){
        Calendar a = Calendar.getInstance();
        final int i = a.get(Calendar.DAY_OF_WEEK);
        qichuang();
        chizaofan();
        if (nohasWeek()&&i>5){
            xiuxi();
        }else {
            shangban();
        }
        chiwanfan();
        shuijiao();
    }

    private final void shuijiao() {
        System.out.println("睡觉");
    }

    private final void chiwanfan() {
        System.out.println("吃晚饭");
    }

    private final void xiuxi() {
        System.out.println("休息");
    }

    private final void shangban() {
        System.out.println("上班");
    }

    boolean nohasWeek() {
        return false;
    }

    private final void chizaofan() {
        System.out.println("吃早饭");
    }

    private final void qichuang() {
        System.out.println("起床");
    }
}
