package com.codepath.thenewyorktimes.controllers;

import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Callback;

/**
 * Created by victor on 3/15/17.
 */

public interface IApiManager {
    void requestSearchArticles(String q, Callback<SearchResults> callback);
}
