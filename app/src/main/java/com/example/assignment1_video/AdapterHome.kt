package com.example.assignment1_video

//import com.example.assignment1_video.Constant.DEVELOPER_K

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1_video.Models.VideoYT
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import androidx.fragment.app.FragmentTransaction;

class AdapterHome(val context: Context, val videoList: List<VideoYT>, var clickListener: OnVideoItemClick):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   // var fragmentB: HomeFragment = HomeFragment()
    inner class YoutubeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var thumbnail:ImageView = itemView.findViewById(R.id.video_thumbnail_image_view)
        var title:TextView = itemView.findViewById(R.id.video_title_label)
        private var duration:TextView = itemView.findViewById(R.id.video_duration_label)

        //@SuppressLint("ResourceType")
        fun setData(item: VideoYT, action: OnVideoItemClick) {
            val getTitle = item.snippet?.title
            val getDuration = item.snippet?.publishedAt
            val getThumb = item.snippet?.thumbnails?.medium?.url
           // val context: Context
//startActivity(Intent(con))
           /* val intent = Intent(context, PlayerFragment::class.java)
            context.startActivity(intent)*/
//var v = data.id
       /*     itemView.setOnClickListener {
/*
                val i = Intent(context, GetStarted::class.java)
                //val i = Intent(context, )
                i.putExtra("video_id", data.id?.videoId)
                i.putExtra("video", getTitle)
                context.startActivity(i)
                //startActivity(i)
*/

                val bundle = Bundle();
                //bundle.putI("String", "String text");
                bundle.putString("video_id", data.id?.videoId)
                //bundle.putString("video", getTitle);
                //bundle.putBoolean("Boolean", Boolean value);
                //val transaction = this.supportFragmentManager.beginTransaction()

                val fragmentB: HomeFragment = HomeFragment()
                fragmentB.arguments = bundle
                //val b = Bundle()
                //bundle.putString("id","id")
                val frag = SearchFragment()
                //frag.arguments=b
                //var fragmentManager: FragmentManager
                /*val fragmentManager = object: FragmentManager()
                {

                }*/
               /* val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                ytPlayer?.let { transaction.replace(R.id.yt, it).commit() }*/
              //  var manager: FragmentManager= fra
               // val f2:SearchFragment = FragmentManager.findFragmentById()
/*
                var fragmentManager = (javaClass as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentManager.replace(R.id.search, fragmentB)
                    .commit()*/
            }

*/
            itemView.setOnClickListener { action.onItemClick(item, adapterPosition) }


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

            /*fun initialize(item: VideoYT, action: OnVideoItemClick){
                //title.text=item
            }*/
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
        yth.setData(videoYT, clickListener)
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
interface OnVideoItemClick{
    fun onItemClick(item: VideoYT, position: Int)
}