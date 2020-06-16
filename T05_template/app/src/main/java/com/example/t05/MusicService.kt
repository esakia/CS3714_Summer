package com.example.t05

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }


    companion object {
        val COMPLETE_INTENT = "complete intent"
        val MUSICNAME = "music name"
    }

    var musicPlayer: MusicPlayer? = null

    private val iBinder = MyBinder()

inner class MyBinder: Binder(){
    fun getService():MusicService {

        return this@MusicService
    }

}

    override fun onCreate() {
        super.onCreate()
        musicPlayer = MusicPlayer(this)
    }


    fun startMusic(){
        musicPlayer?.playMusic()
    }

    fun pauseMusic(){
        musicPlayer?.pauseMusic()
    }


    fun resumeMusic(){
        musicPlayer?.resumeMusic()
    }

    fun getPlayingStatus(): Int {
        return musicPlayer?.getMusicStatus() ?:-1

    }




fun onUpdateMusicName(musicName: String){
    val intent = Intent(COMPLETE_INTENT)
    intent.putExtra(MUSICNAME, musicName)
    sendBroadcast(intent)
}





}