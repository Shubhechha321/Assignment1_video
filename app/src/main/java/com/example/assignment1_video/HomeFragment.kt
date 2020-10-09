package com.example.assignment1_video

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1_video.Models.ModelHome
import com.example.assignment1_video.Models.VideoYT
import com.example.assignment1_video.network.YoutubeAPI
//import com.example.assignment1_video.network.YoutubeAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
   // private var adapter: AdapterHome? = null
    //private var manager: LinearLayoutManager? = null
    //private val videoList:List<VideoYT?> = ArrayList(Arrays.asList(VideoYT))
    //private val videoList: List<VideoYT> = ArrayList(listOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View=inflater.inflate(R.layout.fragment_home,container,false)
      //  var rv = view.findViewById<RecyclerView>(R.id.recycler_view)
      //  adapter = context?.let { AdapterHome(it, videoList as List<VideoYT>) }
     //   manager = LinearLayoutManager(context)
      //  rv.adapter = adapter
      //  rv.layoutManager = manager
     //   if (videoList.isEmpty())
     //   {
     //       getJson()
      //  }
        return view
    }
  /*  private fun getJson() {
        val url =
            (YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.chid + YoutubeAPI.mx + YoutubeAPI.ord
                    + YoutubeAPI.part)
        val data: Call<ModelHome>? = getVideo()?.getHomeVideo(url)
       // val data = getVideo()?.getHomeVideo(url)
        data?.enqueue(object: Callback<ModelHome> {
            override fun onFailure(call: Call<ModelHome>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t);
                //  loading1.setVisibility(View.GONE);
                //loading2.setVisibility(View.GONE);
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ModelHome>, response: Response<ModelHome>) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse: " + response.errorBody() );
                    //loading1.setVisibility(View.GONE);
                    //loading2.setVisibility(View.GONE);
                    Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                } else {
                    val mh = response.body()
                    // videoList.addAll(mh.getItems())
                    /* videoList.run {
                            // videoList.addAll(mh.getItems())
                            addAll(mh?.getItems())
                        }*/
                    //(videoList as MutableCollection<in List<VideoYT>>).addAll(mh.getItems())
                    //Collections.addAll(videoList, mh?.getItems())
                    Collections.addAll(videoList as MutableCollection<in List<VideoYT>?>,mh?.getItems())
                    adapter?.notifyDataSetChanged()
                    // loading1.setVisibility(View.GONE)
                    // loading2.setVisibility(View.GONE)
                    if (mh != null) {
                        if (mh.getNextPageToken() != null) {
                            // nextPageToken = mh.getNextPageToken()
                        }
                    }
                }
            }

        });

    }*/
}


