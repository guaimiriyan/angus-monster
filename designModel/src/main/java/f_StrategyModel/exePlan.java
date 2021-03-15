package f_StrategyModel;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName exePlan.java
 * @Description TODO
 * @createTime 2021年03月15日 22:52:00
 */
public class exePlan {
    private plan plan;

    exePlan(plan plan){
        this.plan = plan;
    }
    public void execute(){
        plan.doPlan();
    }
}
