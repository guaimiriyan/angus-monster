package f_StrategyModel;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName innerExeplan.java
 * @Description TODO
 * @createTime 2021年03月15日 23:09:00
 */
public class innerExeplan {

    public void exePlan(planStrategy.planType planType){
        planStrategy.getPlan(planType).doPlan();
    }
}
