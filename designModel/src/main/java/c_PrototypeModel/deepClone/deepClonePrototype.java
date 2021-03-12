package c_PrototypeModel.deepClone;

import c_PrototypeModel.prototype.Home;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName deepClonePrototype.java
 * @Description TODO
 * @createTime 2021年03月12日 12:44:00
 */
public class deepClonePrototype {


    public static void main(String[] args) {
         deepChinese deepChinese = new deepChinese("angus",10,new Home("cangshengdasha",10000));;
         c_PrototypeModel.deepClone.deepChinese deep1 = deepChinese.cloneMethod1();
         c_PrototypeModel.deepClone.deepChinese deep2 = deepChinese.cloneMethod2();
         System.out.println(deepChinese.getHome() == deep1.getHome());
         System.out.println(deepChinese.getHome() == deep2.getHome());
    }
}
