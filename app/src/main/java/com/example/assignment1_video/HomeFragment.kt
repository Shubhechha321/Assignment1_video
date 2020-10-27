package com.example.assignment1_video

//import com.example.assignment1_video.network.YoutubeAPI
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.telephony.mbms.DownloadRequest
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.custom_controller.view.*
import java.util.*


class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //private val btnStartDownload: Button? = null
    private var downloading = false
    //private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  /*  private val callback: DownloadProgressCallback = object : DownloadProgressCallback() {
        fun onProgressUpdate(progress: Float, etaInSeconds: Long) {
            runOnUiThread {
                progressBar.setProgress(progress.toInt())
                tvDownloadStatus.setText("$progress% (ETA $etaInSeconds seconds)")
            }
        }
    }
*/



    lateinit var exoPlayer: ExoPlayer
    lateinit var simple_exoplayer_view: PlayerView
    private var playbackPos: Long = 0 //The current position of the playback
    lateinit var video_id: String
    var fullscreen: Boolean = false
    val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(context!!, "sample")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //   getSupportActionBar().setTitle("");



        val v: View = inflater.inflate(R.layout.fragment_home, container, false)

        var btnStartDownload: Button=v.findViewById(R.id.download_button)

        val v_title: String? = arguments?.getString("v_title")
        val v_channel_title: String? = arguments?.get("v_channel_title") as String?
        val v_publish_at: String? = arguments?.get("v_published_at") as String?
        val v_description: String? = arguments?.get("v_description") as String?
        val v_url = arguments?.get("v_thumbnail_url") as String?
        video_id = (arguments?.get("video_id") as String?).toString()
        var item_title: TextView = v.findViewById(R.id.item_title)
        var vchannel_title: TextView = v.findViewById(R.id.vchannel_title)
        var published_at: TextView = v.findViewById(R.id.published_at)
        var description: TextView = v.findViewById(R.id.description)
        item_title.setText(arguments?.getString(v_title))
        vchannel_title.setText(v_channel_title)
        published_at.setText(v_publish_at)
        description.setText(v_description)
        simple_exoplayer_view = v.findViewById(R.id.simple_exoplayer_view)
       // val h: SimpleExoPlayer by lazy {  }
        val v_thumbnail: ImageView = v.findViewById(R.id.v_thumbnail)
        Glide.with(this).load(v_url.toString()).into(v_thumbnail)
        if (savedInstanceState != null) {
            //Setting playback position
            playbackPos = savedInstanceState.getLong("PLAYBACK_POS")
            Log.v("VIDEO ID ", video_id)
            initializePlayer(savedInstanceState.getString("video_id")!!)
        }

        initializePlayer(video_id)
        simple_exoplayer_view.keepScreenOn;

        /*simple_exoplayer_view.btn_fullscreen.setOnClickListener {
          change()
        }*/
        simple_exoplayer_view.replay.setOnClickListener {
            exoPlayer.seekTo(exoPlayer.currentPosition - 10 * 1000)
        }
        simple_exoplayer_view.forward.setOnClickListener {
            exoPlayer.seekTo(exoPlayer.currentPosition + 10 * 1000)
        }
        simple_exoplayer_view!!.controllerHideOnTouch = true
        requireActivity()?.getWindow()?.getDecorView()
            ?.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
            )
        /*btnStartDownload.setOnClickListener {
            val d=object DownloadRequest
        }*/

        return v
    }




    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Saving the playback position
        outState.putLong("PLAYBACK_POS", exoPlayer.currentPosition)

        //Saving the video id
        outState.putString("v_id", video_id)
    }

    override fun onStop() {
        super.onStop();

        //Releasing the player
        exoPlayer.release()
    }

    fun initializePlayer(video_id: String) {
        /**Initializes the Exoplayer**/

        //Saving the video id
        this.video_id = video_id
        val h= DefaultRenderersFactory(context)
        val t=DefaultTrackSelector()
        val d=DefaultLoadControl()
        if (!this::exoPlayer.isInitialized) exoPlayer =
            ExoPlayerFactory.newSimpleInstance(context, t, d) //Getting the player
        if (simple_exoplayer_view.player == null) simple_exoplayer_view.player = exoPlayer

        exoPlayer.playWhenReady = true



        //Extracting youtube url
        val youtubeExtractor = @SuppressLint("StaticFieldLeak")
        object : YouTubeExtractor(context!!) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {
                    var downloadUrl: String = ""
                    var iTag: Int = 0
                    for (i in 0..ytFiles.size()) {

                        iTag = ytFiles.keyAt(i)
                        if (ytFiles.get(iTag) != null) {
                            downloadUrl = ytFiles.get(iTag).url
                            break
                        }
                    }
                    Log.d("Itag", iTag.toString())
                    exoPlayer.prepare(
                        ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(downloadUrl))
                    ) //Creating and setting the media source
                    exoPlayer.seekTo(playbackPos)
                }
            }

        }

        //Building the data source
        val videoUrl: String =
            "https://www.youtube.com/watch?v=${video_id}" //Creating the youtube url
        youtubeExtractor.extract(
            videoUrl,
            true,
            true
        ) //Extracting usable url and preparing to play video
        Log.v("url", videoUrl)
    }

   /* fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                === PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    context ,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else {
            true
        }
    }*/
}


