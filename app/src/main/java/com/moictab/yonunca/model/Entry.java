package com.moictab.yonunca.model;

/**
 * Created by moict on 03/12/2017.
 */

public class Entry {
    public String message;
    public String user;
    public int type;

    public Entry(String message, String user, int type) {
        this.message = message;
        this.user = user;
        this.type = type;
    }
}
