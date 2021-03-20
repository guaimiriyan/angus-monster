package i_DecoratorModel.BatterCack;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BetterBatterCack.java
 * @Description TODO
 * @createTime 2021年03月16日 23:21:00
 */
public class BetterBatterCack extends BatterCack{
    @Override
    protected String getMsg() {
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
