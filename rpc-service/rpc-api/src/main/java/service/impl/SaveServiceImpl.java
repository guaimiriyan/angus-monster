package service.impl;

import annotation.rpcService;
import service.ISaveService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName SaveServiceImpl.java
 * @Description TODO
 * @createTime 2021年04月16日 09:51:00
 */
@rpcService(value = ISaveService.class,version = "V1.0")
public class SaveServiceImpl implements ISaveService {
    @Override
    public void saveObj() {
        System.out.println("正在处理保存逻辑");
    }
}
