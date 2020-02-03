package com.example.andriodlabs;

public class Message {

    private String msg;
    private boolean isSend;

    public Message(String msg, boolean isSend) {
        this.msg = msg;
        this.isSend = isSend;
    }

    public Message() {
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
}
