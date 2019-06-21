package cn.xintian.domain;



/**
 * 记录发送失败的用户
 */
public class SendFailure {
    //表的自增id
    private int id;
    //用户的openId
    private String openId;
    //发送消息的时间
    private String sendData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    @Override
    public String toString() {
        return "SendFailure{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", sendData=" + sendData +
                '}';
    }
}
