package SingletonModel.Serializable;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName serializableSingleton.java
 * @Description TODO
 * @createTime 2021年03月11日 17:22:00
 */
public class serializableSingleton implements Serializable {
    private static final serializableSingleton instance = new serializableSingleton();

    private serializableSingleton(){}

    public static final serializableSingleton getInstance(){
        return  instance;
    }

    /**
     *
     * 使用readResolve解决序列化，其方法的返回值必须是Object
     * 原理可以查看ObjectInputstream.readObject()方法进行控制。
     *  if (obj != null &&
     *             handles.lookupException(passHandle) == null &&
     *             desc.hasReadResolveMethod())
     *         {
     *             Object rep = desc.invokeReadResolve(obj);
     *
     * 当反射获取的对象不为空时，且类中存在readResolve方法，则会及进行替换。
     */
    public Object readResolve(){
        return instance;
    }
}
