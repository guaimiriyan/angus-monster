package d_ProxyModel.dynamicModel.CGlibProxy;

import d_ProxyModel.noImplAngus;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CGlibProxyTest.java
 * @Description TODO
 * @createTime 2021年03月15日 09:56:00
 */
public class CGlibProxyTest {

    public static void main(String[] args) {
        CGlibzhongjie cGlibzhongjie = new CGlibzhongjie();
        noImplAngus instance = (noImplAngus)cGlibzhongjie.getInstance(noImplAngus.class);
        instance.zhaofang();
    }
}
