package com.codepath.thenewyorktimes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("web_url")
    String webUrl;
    String snippet;
    List<Multimedia> multimedia;

}
