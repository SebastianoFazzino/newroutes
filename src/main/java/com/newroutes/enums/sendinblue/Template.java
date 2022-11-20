package com.newroutes.enums.sendinblue;

import lombok.Getter;

@Getter
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

    public static Template getById(long id) {
        switch ((int) id)  {
            case 2:
            default:
                return WELCOME;
        }
    }

    public static Template getByTag(String tag) {
        switch (tag) {
            case "welcome":
            default:
                return WELCOME;
        }
    }
}
