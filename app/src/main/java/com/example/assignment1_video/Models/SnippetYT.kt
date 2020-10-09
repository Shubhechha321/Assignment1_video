package com.example.assignment1_video.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SnippetYT {
    @SerializedName("publishedAt")
    @Expose
     var publishedAt: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
     var description: String? = null

    @SerializedName("thumbnails")
    @Expose
    var thumbnails: ThumbnailYT? = null


}