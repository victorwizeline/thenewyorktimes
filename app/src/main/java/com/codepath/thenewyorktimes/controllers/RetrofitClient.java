package com.codepath.thenewyorktimes.controllers;

import com.codepath.thenewyorktimes.interfaces.NewYorkTimesApi;
import com.codepath.thenewyorktimes.interfaces.NewYorkTimesClient;
import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codepath.thenewyorktimes.BuildConfig.NYT_API_KEY;
import static com.codepath.thenewyorktimes.utils.Constants.API_URL;

public class RetrofitClient implements NewYorkTimesClient {

    private NewYorkTimesApi newYorkTimesApi;

    public RetrofitClient() {
        newYorkTimesApi = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewYorkTimesApi.class);
    }

    @Override
    public void requestSearchArticles(String q, int page, Callback<SearchResults> callback) {
        newYorkTimesApi.requestSearchArticles(NYT_API_KEY, q, String.valueOf(page)).enqueue(callback);
    }
}
