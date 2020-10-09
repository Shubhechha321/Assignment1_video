package com.example.assignment1_video

//import com.example.assignment1_video.Constant.DEVELOPER_K

import android.R.attr
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1_video.Models.VideoYT
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class AdapterHome(val context: Context, val videoList: List<VideoYT>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class YoutubeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var thumbnail:ImageView = itemView.findViewById(R.id.video_thumbnail_image_view)
        var title:TextView = itemView.findViewById(R.id.video_title_label)
        private var duration:TextView = itemView.findViewById(R.id.video_duration_label)
        fun setData(data: VideoYT) {
            val getTitle = data.snippet?.title
            val getDuration = data.snippet?.publishedAt
            val getThumb = data.snippet?.thumbnails?.medium?.url
           // val context: Context
//startActivity(Intent(con))
           /* val intent = Intent(context, PlayerFragment::class.java)
            context.startActivity(intent)*/

            itemView.setOnClickListener {

                val i = Intent(context, HomeFragment::class.java)
               // i.putExtra("video_id", data.id)
               // i.putExtra("video_title", title)

                context.startActivity(i)
            }



            title.text = getTitle
            duration.text=getDuration
            Picasso.with(itemView.context).load(getThumb).placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(thumbnail, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "")
                    }

                    override fun onError() {
                        Log.e(TAG, "Thumbnail error: ")
                    }

                })
        }
    }


    /*override fun getItemCount(): Int {
        return videoList?.size!!
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.youtube_search_layout, parent, false)
        return YoutubeHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      /*  val videoYT: VideoYT? = videoList?.get(position)
              val yth = holder as YoutubeHolder
              if (videoYT != null) {
            yth.setData(videoYT)
        }*/
        val videoYT = videoList!![position]
        val yth = holder as YoutubeHolder
        yth.setData(videoYT)
      /*  holder.itemView.setOnClickListener {
            val i = Intent(this@YoutubeHolder, DashBoardActivity::class.java)
            startActivity(i)
        }*/
       /* val context=holder.title.context

        val intent = Intent( context, PlayerFragment::class.java)
        context.startActivity(intent)*/

    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}
