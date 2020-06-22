package com.example.pp05

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicService: Service() {

    var musicPlayer: MusicPlayer? = null


    companion object{
    val COMPLETE_INTENT = "complete intent"
    val MUSICNAME = "music name"}


    inner class MyBinder: Binder(){
        fun getService():MusicService{
            return this@MusicService
        }
    }

    private val iBinder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? {
       return iBinder
    }

    fun onUpdateMusicName(musicName: String) {

        val intent = Intent(COMPLETE_INTENT)
        intent.putExtra(MUSICNAME, musicName)
        sendBroadcast(intent)
    }


    override fun onCreate() {
        super.onCreate()
        musicPlayer = MusicPlayer(this)
    }

    fun playEffect() {
        musicPlayer?.playRandomEffectDelayed()
    }

    fun startMusic() {
        musicPlayer?.playMusic()
    }

    fun pauseMusic() {
        musicPlayer?.pauseMusic()
    }

    fun resumeMusic() {
        musicPlayer?.resumeMusic()
    }

    fun getPlayingStatus(): Int {
        return musicPlayer?.getMusicStatus() ?: -1
    }





}