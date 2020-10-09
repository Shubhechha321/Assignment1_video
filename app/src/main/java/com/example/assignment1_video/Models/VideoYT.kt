package com.example.assignment1_video.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class VideoYT {


    @SerializedName("id")
    @Expose
     var id: VideoID? = null

    @SerializedName("snippet")
    @Expose
    var snippet: SnippetYT? = null




}/*
class VideoYT {
    @SerializedName("id")
    @Expose
    lateinit var id:VideoID
    @SerializedName("snippet")
    @Expose
    lateinit var snippet:SnippetYT
    constructor() {}
    constructor(id:VideoID, snippet:SnippetYT) {
        this.id = id
        this.snippet = snippet
    }
}*/