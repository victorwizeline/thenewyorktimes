package com.codepath.thenewyorktimes.controllers;

import com.codepath.thenewyorktimes.interfaces.NewYorkTimesClient;
import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codepath.thenewyorktimes.BuildConfig.NYT_SEARCH_API_KEY;
import static com.codepath.thenewyorktimes.utils.Constants.API_URL;

public class ApiManager implements IApiManager {

    private NewYorkTimesClient newYorkTimesClient;

    public ApiManager() {
        newYorkTimesClient = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewYorkTimesClient.class);
    }

    @Override
    public void requestSearchArticles(String q, Callback<SearchResults> callback) {
        newYorkTimesClient.getSearchArticles(NYT_SEARCH_API_KEY, q).enqueue(callback);
    }
}
