package protocol;

import org.msgpack.annotation.Message;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName angusMessage.java
 * @Description TODO
 * @createTime 2021年04月23日 10:18:00
 */
@Message
public class angusMessage {
    private String cmd;
    private String nickName;
    private long sendTime;
    private String sendMsg;

    public angusMessage(String logout, String nickname, long currentTimeMillis, String sendMsg) {
        this.cmd = logout;
        this.nickName = nickname;
        this.sendTime = currentTimeMillis;
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
