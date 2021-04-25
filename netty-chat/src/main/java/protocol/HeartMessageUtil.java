package protocol;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName HeartMessageUtil.java
 * @Description TODO
 * @createTime 2021年04月25日 21:22:00
 */
public class HeartMessageUtil {

    public static  String ClientNickName;

    public static int HearBeatTime = 10;

    public static angusMessage getClientHeartMsg(){
        return new angusMessage(ChatCmdType.HEART.cmdStr,ClientNickName,System.currentTimeMillis(),ClientNickName+" send heart beat！");
    }
}
