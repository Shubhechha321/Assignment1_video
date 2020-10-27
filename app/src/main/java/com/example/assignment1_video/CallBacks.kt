package com.example.assignment1_video

import com.google.android.exoplayer2.SimpleExoPlayer

interface CallBacks {
    fun callbackObserver(obj: Any?)
    interface playerCallBack {
        fun onItemClickOnItem(albumId: Int?)
        fun onPlayingEnd()
    }
 /*   private fun initializePlayer() {
        val simpleExoplayer = SimpleExoPlayer.Builder(context).build()
        val randomUrl = urlList.random()
        preparePlayer(randomUrl.first, randomUrl.second)
        exoplayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }*/
}