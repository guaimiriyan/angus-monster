package b_SingletonModel.register;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName containerSingleton.java
 * @Description 关于容器式单例的，spring使用的就是这种模式
 * @createTime 2021年03月12日 09:20:00
 */
public class containerSingleton {


    private containerSingleton(){};
    /**
     * 使用容器来确保对象的唯一性
     */
    private static final ConcurrentHashMap<String,Object> container = new ConcurrentHashMap<>();

    public Object getBeanByName(String name){
        synchronized (container){
            if (!container.containsKey(name)){
                //此处可以使用简单工厂来创建单项
                containerSingleton containerSingleton = new containerSingleton();
                container.put(name,containerSingleton);
                return containerSingleton;
            }
            return container.get(name);
        }

    }

    public Object getBeanByClass(String className){
        synchronized (container){
            try {
                if (!container.containsKey(className)){
                    Class<?> aClass = Class.forName(className);
                    Object object = aClass.newInstance();
                    container.put(className,object);
                    return object;
                }
            }catch (Exception e){

            }
            return container.get(className);
        }

    }
}
