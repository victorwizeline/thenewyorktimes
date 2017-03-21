package com.codepath.thenewyorktimes.interfaces;

import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Callback;

public interface NewYorkTimesClient {
    void requestQueriedArticles(String q, int page, Callback<SearchResults> callback);
    void requestQueriedArticles(int page, Callback<SearchResults> callback);
    void requestFilteredArticles(String date, String sort, String newsDesk, int page, Callback<SearchResults> callback);
}
