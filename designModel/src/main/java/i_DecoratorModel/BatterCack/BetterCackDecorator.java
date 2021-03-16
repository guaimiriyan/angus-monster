package i_DecoratorModel.BatterCack;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BetterCackDecorator.java
 * @Description TODO
 * @createTime 2021年03月16日 23:23:00
 */
public abstract class BetterCackDecorator extends BatterCack{
    BatterCack batterCack;
    BetterCackDecorator(BatterCack batterCack){
        this.batterCack = batterCack;
    }
    @Override
    protected String getMsg() {
        return batterCack.getMsg();
    }

    @Override
    protected int getPrice() {
        return batterCack.getPrice();
    }
}
