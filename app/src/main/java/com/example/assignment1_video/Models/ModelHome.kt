package com.example.assignment1_video.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ModelHome {
    @SerializedName("nextPageToken")
    @Expose
     var nextPageToken: String? = null

    @SerializedName("items")
    @Expose
    var items: List<VideoYT>? = null
   // fun ModelHome() {}

   /* fun ModelHome(
        nextPageToken: String?,
        items: List<VideoYT>?
    ) {
        this.nextPageToken = nextPageToken
        this.items = items
    }*/
}