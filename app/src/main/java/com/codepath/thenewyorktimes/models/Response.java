package com.codepath.thenewyorktimes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    private List<Article> articles;
    private Meta meta;

    public List<Article> getArticles() {
        return articles;
    }

    public Meta getMeta() {
        return meta;
    }
}
