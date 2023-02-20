package com.jkb.junbin.sharing.model;

public class Message {
    public int id;
    public String content;
    public long date;
    public String fileName;


    public Message(int id, String content ,String fileName,long date) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.fileName = fileName;
    }
}
