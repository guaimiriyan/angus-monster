package j_ObserverModel.core;

import j_ObserverModel.core.Event;
import j_ObserverModel.exe.MyCallBack;
import j_ObserverModel.exe.MyEventType;
import javafx.event.EventType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName EvenListener.java
 * @Description TODO
 * @createTime 2021年03月17日 15:03:00
 */
public class EvenListener {

    public static final Map<MyEventType, Event> events = new HashMap<>();
    //private EvenListener(){}

    /**
     *
     * 最终目的就是为了将callback调用，监听池子里是否有东西在调用
     * 然后调用callback
     */
    public void addListener(MyEventType eventType, Object callBack){
        //产生新的Event,注册道事件池里
        final Method targetMethod;
        try {
            /**
             * 这里只是添加了无参数条件下的回调函数
             */
            targetMethod = callBack.getClass().getSuperclass().getMethod("on" + getUpCaseFirstChar(eventType.toString().toLowerCase()), Event.class);
            this.addListener(eventType,callBack,targetMethod);
        } catch (NoSuchMethodException e) {
            System.out.println("定义回调函数时，请使用动作on加事件驼峰！");
            e.printStackTrace();
        }

    }

    private void addListener(MyEventType eventType, Object callBack, Method targetMethod) {
        events.put(eventType,new Event(callBack,targetMethod));
    }


    protected void trigger(MyEventType eventType){
        if (!events.containsKey(eventType)) return;

        this.trigger(events.get(eventType));
    }

    private void trigger(Event event) {
        event.setSource(this);
        event.setTimesamp(String.valueOf(System.currentTimeMillis()));
        //反射执行callback
        try {
            event.getTargetMethod().invoke(event.getTarget(),event);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * 处理大小写的方式
     */

    String getUpCaseFirstChar(String eventType){
        final char[] chars = eventType.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
