package com.codepath.thenewyorktimes.interfaces;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface ApiManagerInterface {

    @POST("articlesearch.json/")
    Call<String> getSearchArticle(@Field("api-key") String apiKey, @Field("page") String page, @Field("q") String q);
}
