package com.example.assignment1_video

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1_video.network.YoutubeAPI
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

//class GetStarted : YouTubeBaseActivity() {}

class GetStarted : YouTubeBaseActivity() {
    private var ytPlayer: YouTubePlayerView? = null
    private lateinit var videoId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
        ytPlayer = findViewById(R.id.yt)
        videoId = intent.getStringExtra("video_id").toString()
        initializePlayer()

    }
    private fun initializePlayer(){
        ytPlayer?.initialize(YoutubeAPI.KEY,object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if(!p2){
                    p1?.loadVideo(videoId)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

    }
}
 //*/
