package f_StrategyModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName planStrateg.java
 * @Description TODO
 * @createTime 2021年03月15日 22:55:00
 */
public class planStrategy {

    private planStrategy(){}

    private static final Map<planType,plan> planMap = new HashMap();

    static {
        planMap.put(planType.plan_a,new planA());
        planMap.put(planType.plan_b,new planB());
        planMap.put(planType.plan_c,new planC());
    }


    public static plan getPlan(planType planType){
        if (!planMap.containsKey(planType)){
            return planMap.get(planType.plan_a);
        }
        return planMap.get(planType);
    }

    static enum planType{
        plan_a,plan_b,plan_c;
    }
}
