package com.example.assignment1_video.Retrofit

import com.google.gson.annotations.SerializedName




data class Main (


    @SerializedName("videoId")
    var videoId: String,

    @SerializedName("title")
    var title: String,


    @SerializedName("url")
    var url: String



)