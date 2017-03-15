package com.codepath.thenewyorktimes.controllers;

import com.codepath.thenewyorktimes.BuildConfig;
import com.codepath.thenewyorktimes.interfaces.ApiManagerInterface;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private ApiManagerInterface apiManagerInterface;

    public ApiManager() {
        apiManagerInterface = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiManagerInterface.class);
    }

    public void get(String q, Callback<String> callback) {
        apiManagerInterface.getSearchArticle(BuildConfig.NYT_SEARCH_API_KEY, "0", q).enqueue(callback);
    }
}
