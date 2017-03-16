package com.codepath.thenewyorktimes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("web_url")
    private String webUrl;
    private Headline headline;
    @SerializedName("lead_paragraph")
    private String leadParagraph;
    private List<Multimedia> multimedia;

    public String getWebUrl() {
        return webUrl;
    }

    public Headline getHeadline() {
        return headline;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }
}
