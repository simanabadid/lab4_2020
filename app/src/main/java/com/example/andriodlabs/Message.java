package com.example.andriodlabs;

public class Message {

    private String msg;
    private boolean isSend;
    private long msgID;

    public Message(String msg, boolean isSend, long msgID) {
        this.msg = msg;
        this.isSend = isSend;
        this.msgID = msgID;
    }

    public Message(String msg, boolean isSend) {
        this(msg, isSend,0);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getMsgID(){
        return msgID;
    }

    public void setMsgID(long msgId){
        this.msgID = msgID;
    }
}
