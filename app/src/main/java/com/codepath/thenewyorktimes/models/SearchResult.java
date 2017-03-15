package com.codepath.thenewyorktimes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("docs")
    List<Article> articles;
}
