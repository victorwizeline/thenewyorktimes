package com.codepath.thenewyorktimes.models;

import com.bumptech.glide.Glide;
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

    private List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public String getWideImage() {
        for (Multimedia multimedia : getMultimedia()) {
            if (multimedia.getSubtype() != null && multimedia.getSubtype().equalsIgnoreCase("wide")) {
                return  multimedia.getUrl();
            }
        }
        return "";
    }
}
