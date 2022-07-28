package com.vincent78.jvm.framework.model;

import java.util.Date;

public class MemObj {
    private byte[] content;
    private long timestamp;


    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static MemObj generate(int size) {
        MemObj obj = new MemObj();
        obj.setTimestamp(new Date().getTime());
        byte[] content = new byte[size];
        obj.setContent(content);
        return obj;
    }
}
