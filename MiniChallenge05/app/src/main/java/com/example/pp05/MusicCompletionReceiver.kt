package com.example.pp05
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

class MusicCompletionReceiver(val mainActivity: MainActivity?=null) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val musicName = intent.getStringExtra(MusicService.MUSICNAME)
         mainActivity?.updateName(musicName)
    }
}