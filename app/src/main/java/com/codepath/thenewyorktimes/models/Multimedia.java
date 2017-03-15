package com.codepath.thenewyorktimes.models;

import static com.codepath.thenewyorktimes.utils.Constants.BASE_URL;

public class Multimedia {

    private String url;
    private String subtype;
    private int width;
    private int height;
    private String type;

    public String getUrl() {
        return BASE_URL + url;
    }

    public String getSubtype() {
        return subtype;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }
}
