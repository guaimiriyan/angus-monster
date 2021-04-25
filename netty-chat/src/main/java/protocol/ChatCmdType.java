package protocol;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ChatCmdType.java
 * @Description TODO
 * @createTime 2021年04月24日 01:45:00
 */
public enum ChatCmdType {
    LOGIN("login"),
    CHAT("chat"),
    LOGOUT("logout"),
    HEART("heart");

    /**
     * 在这里涉及好angusMessage的格式说明
     * |cmd|-|time|-|nickname|-|sendMsg|
     */
    public static boolean isAngusMessage(String msg){
        //这里可以使用正则
        return true;
    }

    public static boolean isAnsgusMessage(angusMessage msg){
        final String cmd = msg==null?"": msg.getCmd();
        if ("login,chat,logout".contains(cmd)) return true;
        return false;
    }
    public String cmdStr;
    ChatCmdType(String cmdStr){
        this.cmdStr = cmdStr;
    }

}
