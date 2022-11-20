package com.newroutes.enums.sendinblue;

public enum Template {

    WELCOME(2, "welcome");


    //************************

    private final long id;
    private final String tag;

    Template(long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public long getId() { return id; }
    public String getTag() { return tag; };
}
