package i_DecoratorModel.BatterCack;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EggDecorator.java
 * @Description TODO
 * @createTime 2021年03月16日 23:25:00
 */
public class EggDecorator extends BetterCackDecorator{
    EggDecorator(BatterCack batterCack) {
        super(batterCack);
    }

    @Override
    protected String getMsg() {
        return super.getMsg()+" 一个鸡蛋";
    }

    @Override
    protected int getPrice() {
        return super.getPrice()+1;
    }

}
