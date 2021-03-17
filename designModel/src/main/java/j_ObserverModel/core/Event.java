package j_ObserverModel.core;

import sun.plugin2.jvm.RemoteJVMLauncher;

import java.lang.reflect.Method;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Event.java
 * @Description 观察者正真观察的时间
 * @createTime 2021年03月17日 14:20:00
 */
public class Event {

    private Object Source;
    private Object target;
    //这个东西就相当于回调函数
    private Method targetMethod;
    private String eventType;
    private String timesamp;

    public Event(Object target, Method targetMethod) {
        this.target = target;
        this.targetMethod = targetMethod;
    }

    public Object getSource() {
        return Source;
    }

    public void setSource(Object source) {
        Source = source;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getTimesamp() {
        return timesamp;
    }

    public void setTimesamp(String timesamp) {
        this.timesamp = timesamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Source=" + Source +
                ", target=" + target +
                ", targetMethod=" + targetMethod +
                ", eventType='" + eventType + '\'' +
                ", timesamp='" + timesamp + '\'' +
                '}';
    }
}
