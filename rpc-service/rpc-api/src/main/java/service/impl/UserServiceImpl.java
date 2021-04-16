package service.impl;

import annotation.rpcService;
import domain.User;
import service.IUserService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月16日 09:49:00
 */
@rpcService(value = IUserService.class,version = "V2.0")
public class UserServiceImpl implements IUserService {
    @Override
    public String dealUser(User user) {
        System.out.println("正在处理用户对象："+user);
        return user.toString();
    }
}
