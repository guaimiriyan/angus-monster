package protocol;

import org.msgpack.annotation.Message;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName angusMessage.java
 * @Description TODO
 * @createTime 2021年04月24日 01:42:00
 */
@Message
public class angusMessage {
    String cmd;
    String nickName;
    long sendTime;
    String sendMsg;

    public angusMessage(){}

    public angusMessage(String cmd, String nickName, long sendTime, String sendMsg) {
        this.cmd = cmd;
        this.nickName = nickName;
        this.sendTime = sendTime;
        this.sendMsg = sendMsg;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    @Override
    public String toString() {
        return "angusMessage{" +
                "cmd='" + cmd + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sendTime=" + sendTime +
                ", sendMsg='" + sendMsg + '\'' +
                '}';
    }
}
