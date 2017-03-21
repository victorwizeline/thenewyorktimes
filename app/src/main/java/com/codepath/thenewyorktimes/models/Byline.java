package com.codepath.thenewyorktimes.models;

public class Byline {

    private String original;

    public String getOriginal() {
        if (original != null){
            return original;
        } else {
            return "by N/A";
        }
    }
}
