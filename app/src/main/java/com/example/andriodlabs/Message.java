

package com.example.andriodlabs;

public class Message{
    String message;
    boolean send;
    long id;

    public Message(String s, boolean b , long i)
    {
        setMessage(s);
        setSend(b);
        id = i;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setSend(boolean send){
        this.send = send;
    }

    public String getMessage(){
        return message;
    }

    public Boolean getSend(){
        return send;
    }

    public Long getId(){
        return id;
    }
}