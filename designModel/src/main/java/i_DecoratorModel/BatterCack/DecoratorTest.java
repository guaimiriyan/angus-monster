package i_DecoratorModel.BatterCack;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName DecoratorTest.java
 * @Description TODO
 * @createTime 2021年03月16日 23:28:00
 */
public class DecoratorTest {
    public static void main(String[] args) {
        BatterCack batterCack = new BetterBatterCack();
        batterCack = new EggDecorator(batterCack);
        System.out.print(batterCack.getMsg()+".........."+batterCack.getPrice());
    }
}
