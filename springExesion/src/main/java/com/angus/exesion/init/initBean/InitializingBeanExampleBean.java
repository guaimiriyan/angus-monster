package com.angus.exesion.init.initBean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName InitializingBeanExampleBean.java
 * @Description TODO
 * @createTime 2021年05月18日 22:44:00
 */
@Component
public class InitializingBeanExampleBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getSimpleName()+"初始化");
    }
}
