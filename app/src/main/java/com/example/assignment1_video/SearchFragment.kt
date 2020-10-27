package com.example.assignment1_video

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1_video.Models.ModelHome
import com.example.assignment1_video.Models.VideoYT
import com.example.assignment1_video.Retrofit.ApiClient
import com.example.assignment1_video.Retrofit.ApiInterface
import com.example.assignment1_video.Retrofit.Main
import com.example.assignment1_video.network.YoutubeAPI
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() , OnVideoItemClick{

    private lateinit var communicator: Communicator


    var rcy = view?.findViewById<RecyclerView>(R.id.recycler_view)
    private var videoList: MutableList<VideoYT> = ArrayList()
    //var mAdapter = context?.let { AdapterHome(it, videoList) }
    lateinit var mAdapter: AdapterHome
  // private var videoList :List<VideoYT>= ArrayList()
    override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false)
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)


        communicator = activity as Communicator


       // val exampleList = generateDummyList()
        var rcy = view.findViewById<RecyclerView>(R.id.recycler_view)

      //manager = LinearLayoutManager(context)
      var manager = LinearLayoutManager (context)
     // rcy.adapter = this.context?.let { AdapterHome(it, videoList) }
        //rcy.layoutManager = LinearLayoutManager(context)
        //mAdapter = context?.let { AdapterHome(it, videoList) }
      mAdapter = AdapterHome(context!!,videoList, this)
      rcy.setAdapter(mAdapter)
      rcy.layoutManager = manager
       // rcy.setHasFixedSize(true)

        var sch: ImageButton
        sch = view.findViewById(R.id.btn_search)
        sch.setOnClickListener {


            if (!TextUtils.isEmpty(et_search.text.toString())){
                videoList.clear()

                //new line added for warning input connection
                //et_search.text.clear()


                getJson(et_search.text.toString().trim())



               /* ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1)
                fun onRequestPermissionsResult(requestCode:Int,
                                               permissions:Array<String>, grantResults:IntArray) {
                    when (requestCode) {
                        1 -> {
                            // If request is cancelled, the result arrays are empty.
                            if ((grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                            {
                                // permission was granted, yay! Do the
                                // contacts-related task you need to do.
                            }
                            else
                            {
                                // permission denied, boo! Disable the
                                // functionality that depends on this permission.
                                Toast.makeText(context, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
                            }
                            return
                        }
                    }// other 'case' lines to check for other
                    // permissions this app might request
                }
*/




            } else {
                Toast.makeText(context, "You need to enter some text", Toast.LENGTH_SHORT).show();
            }
        }
        return view
    }
    private fun getJson(query: String){
       /* val url: String =
            (YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.chid + YoutubeAPI.mx + YoutubeAPI.ord
                    + YoutubeAPI.part + YoutubeAPI.query + query + YoutubeAPI.type)*/
        val url: String =
            (YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY +  YoutubeAPI.mx + YoutubeAPI.ord
                    + YoutubeAPI.part + YoutubeAPI.query + query + YoutubeAPI.type)
        val data: Call<ModelHome?>? = YoutubeAPI.video?.getHomeVideo(url)
        data!!.enqueue(object : Callback<ModelHome?> {
            override fun onResponse(call: Call<ModelHome?>, response: Response<ModelHome?>) {
                if (response.errorBody() != null) {
                    Log.w(TAG, "onResponse search : " + response.errorBody().toString())
                } else {
                    val mh = response.body()
                    if (mh!!.items!!.size != 0) {
                        mh.items?.let { videoList.addAll(it) }
                        mAdapter?.notifyDataSetChanged()
                        //adapter.notifyDataSetChanged()
                        //AdapterHome.YoutubeHolder


                       /* val v = arguments?.getString("id").toString()
                        if(v=="id"){
                            var fragmentManager = (context as Fragment).supportFragmentManager.beginTransaction()
                            fragmentManager.replace(R.id., fragmentB)
                                .commit()
                        }*/






                    } else {
                        Toast.makeText(context, "No video", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ModelHome?>, t: Throwable) {
                Log.e(TAG, "onFailure search: ", t)
            }
        })
    }

    override fun onItemClick(item: VideoYT, position: Int) {
        Toast.makeText(context,item.id?.videoId, Toast.LENGTH_SHORT).show()
        communicator.passData(item.id?.videoId.toString(), item.snippet?.title.toString())
    }
}