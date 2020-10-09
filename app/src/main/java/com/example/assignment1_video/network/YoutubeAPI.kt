package com.example.assignment1_video.network

import com.example.assignment1_video.Models.ModelHome
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


//val KEY = "key=AIzaSyBaOHNupdT4-hf-p1_6EjCu0PCtLMHl9L8"

object YoutubeAPI {
    const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    //const val CHANNEL_ID = "UCkXmLjEr95LVtGuIm3l2dPg"
    const val KEY = "key=AIzaSyBaOHNupdT4-hf-p1_6EjCu0PCtLMHl9L8"
    const val sch = "search?"
   // const val chid = "&channelId=" + CHANNEL_ID
    const val mx = "&maxResults=1000"
    const val ord = "&order=date"
    const val part = "&part=snippet"
    const val NPT = "&pageToken="
    const val ply = "playlists?"
    const val part_ply = "&part=snippet,contentDetails"
    const val query = "&q="
    const val type = "&type=video"
    const val CH = "channels?"
    //const val IDC = "&id=" + CHANNEL_ID
    const val CH_PART = "&part=snippet,statistics,brandingSettings"
    var video: Video? = null
        get() {
            if (field == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                field = retrofit.create(Video::class.java)
            }
            return field
        }
        private set

    interface Video {
        @GET
        fun getHomeVideo(@Url url: String?): Call<ModelHome?>?

        /*@GET
        fun getPlaylist(@Url url: String?): Call<ModelPlaylist?>?

        @GET
        fun getChannel(@Url url: String?): Call<ModelChannel?>?*/
    }
}