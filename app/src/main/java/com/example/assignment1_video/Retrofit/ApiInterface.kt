package com.example.assignment1_video.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
  //  @GET("search?key=AIzaSyBaOHNupdT4-hf-p1_6EjCu0PCtLMHl9L8")
    @GET("/youtube/v3/search")
    fun getSearchData(@Query("") name: String?): Call<List<Main>>

}/*
const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

interface MoviesApi {

    @GET("movies")
    fun getMovies() : Call<List<Movie>>

    companion object {
        operator fun invoke() : MoviesApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApi::class.java)
        }
    }*/
