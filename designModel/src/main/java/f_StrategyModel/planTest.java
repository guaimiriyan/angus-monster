package f_StrategyModel;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName planTest.java
 * @Description TODO
 * @createTime 2021年03月15日 23:03:00
 */

/**
 *策略和委派的区别
 * 1、委派注重的向下分发的过程，最初的调用不知道内部选择。1-1-多
 * 2、策略注重的选择的过程，在外部确定所有的策略。多-1-1
 */
public class planTest {
    public static void main(String[] args) {
        //也可以放在外部
        exePlan exePlan = new exePlan(planStrategy.getPlan(planStrategy.planType.plan_a));
        exePlan.execute();

        //策略模式可以将其放在内部
        innerExeplan innerExeplan = new innerExeplan();
        innerExeplan.exePlan(planStrategy.planType.plan_b);
    }
}
