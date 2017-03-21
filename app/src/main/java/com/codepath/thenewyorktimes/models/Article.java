package com.codepath.thenewyorktimes.models;

import android.annotation.SuppressLint;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.codepath.thenewyorktimes.utils.Constants.DATE_FORMAT;

public class Article {

    @SerializedName("web_url")
    private String webUrl;
    private Headline headline;
    @SerializedName("lead_paragraph")
    private String leadParagraph;
    private List<Multimedia> multimedia;
    private Byline byline;

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

    public Byline getByline() {
        if (byline != null){
            return byline;
        } else {
            return new Byline();
        }
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
