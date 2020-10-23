package com.example.assignment1_video

//import com.example.assignment1_video.network.YoutubeAPI
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.YouTubePlayerFragment


class HomeFragment : Fragment() {
    private var ytPlayer: YouTubePlayerFragment? = null
    private lateinit var videoId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        /*    //ytPlayer = fragmentManager as YouTubePlayerFragment?.findViewById(R.id.yt);
        ytPlayer = fragmentManager?.findFragmentById(R.id.yt) as YouTubePlayerFragment?
        // videoId = getIntent("video_id").toString()
        videoId = arguments?.getString("video_id").toString();
        //videoId = intent.getStringExtra("video_id").toString();
        /*val bundle = this.arguments
        if (bundle != null) {
            val i = bundle.getInt(key, defaulValue)
        }
        <com.google.android.youtube.player.YouTubePlayerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/yt"/>*/
        initializePlayer()
*/        return inflater.inflate(R.layout.fragment_home, container, false)
    }

   /* private fun initializePlayer() {
        ytPlayer?.initialize(YoutubeAPI.KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if (!p2) {
                    p1?.loadVideo(videoId)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

    }*/
}
    /*
    ytPlayer?.initialize(YoutubeAPI.KEY, object : YouTubePlayer.OnInitializedListener {
    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {
            p1?.loadVideo(videoId)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
    }
}
}
*/

/*
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }
}*/