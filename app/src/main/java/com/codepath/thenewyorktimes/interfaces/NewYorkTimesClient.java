package com.codepath.thenewyorktimes.interfaces;

import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Callback;

public interface NewYorkTimesClient {
    void requestSearchArticles(String q, String page, Callback<SearchResults> callback);
    void requestRecentArticles(String page, Callback<SearchResults> callback);
}
