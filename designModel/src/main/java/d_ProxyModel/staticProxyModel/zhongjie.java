package d_ProxyModel.staticProxyModel;

import d_ProxyModel.beipiao;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName zhongjie.java
 * @Description TODO
 * @createTime 2021年03月12日 23:42:00
 */
public class zhongjie implements beipiao {

    private beipiao bp;

    public zhongjie(beipiao bp) {
        this.bp = bp;
    }

    @Override
    public void zhaofang() {
        before();
        bp.zhaofang();
        after();
    }

    @Override
    public void zhaogongzuo() {

    }

    private void before(){
        System.out.println("我是中介，你有什么需要帮助的！");
    }

    private void after(){
        System.out.println("我已经帮你找好房子，安心居住！");
    }
}
