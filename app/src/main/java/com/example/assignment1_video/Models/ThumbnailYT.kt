package com.example.assignment1_video.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ThumbnailYT {
    @SerializedName("medium")
    @Expose
    var medium: MediumThumb? = null


    class MediumThumb {
        @SerializedName("url")
        @Expose
        var url: String? = null

    }
}