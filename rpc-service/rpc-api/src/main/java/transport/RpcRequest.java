package transport;

import java.io.Serializable;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RpcRequest.java
 * @Description TODO
 * @createTime 2021年04月16日 11:04:00
 */
public class RpcRequest implements Serializable {
    //1、需要调用接口的类型
    String callClazz;
    //2、需要调用接口的版本
    String callVersion;
    //3、需要调用接口的参数
    Object[] params;
    //4、需调用接口的方法名
    String methodName;

    public String getCallClazz() {
        return callClazz;
    }

    public void setCallClazz(String callClazz) {
        this.callClazz = callClazz;
    }

    public String getCallVersion() {
        return callVersion;
    }

    public void setCallVersion(String callVersion) {
        this.callVersion = callVersion;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
