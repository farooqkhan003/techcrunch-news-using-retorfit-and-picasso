package com.example.hp.newsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {

    @GET("articles")
    Call<Data> getNews(@Query("source") String source , @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);
}