package com.codepath.thenewyorktimes.interfaces;

import com.codepath.thenewyorktimes.models.SearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.codepath.thenewyorktimes.utils.Constants.SEARCH_ARTICLES;

public interface NewYorkTimesApi {

    @GET(SEARCH_ARTICLES)
    Call<SearchResults> requestDefaultArticles(@Query("api-key") String apiKey, @Query("page") String page);

    @GET(SEARCH_ARTICLES)
    Call<SearchResults> requestQueriedArticles(@Query("api-key") String apiKey, @Query("q") String q, @Query("page") String page);

    @GET(SEARCH_ARTICLES)
    Call<SearchResults> requestFilteredArticles(@Query("api-key") String apiKey, @Query("begin_date") String beginDate, @Query("sort") String sort, @Query("fq") String fq, @Query("page") String page);
}
