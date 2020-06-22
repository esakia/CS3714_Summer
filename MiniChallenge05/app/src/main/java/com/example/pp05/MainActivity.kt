package com.example.pp05

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.os.IBinder
import android.view.View
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf


class MainActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        const val TAG = "PP05_TAG"
        const val USERNAME = "CS3714"
        const val URL = "https://webhook.site/"
        const val ROUTE ="78dd7faa-1134-4f2c-acfc-55f7f3a6ae36" //TODO: this is likely to expire. Try your own URL.
        const val INITIALIZE_STATUS = "intialization status"
        const val MUSIC_PLAYING = "music playing"
    }


    override fun onClick(v: View?) {
        if (isBound) {
            when (musicService?.getPlayingStatus()) {
                0 -> {
                    musicService?.startMusic()
                    play?.setText("Pause")
                    appendEvent("Pressed Play")
                }
                1 -> {
                    musicService?.pauseMusic()
                    play?.setText("Resume")
                    appendEvent("Pressed Pause")
                }
                2 -> {
                    musicService?.resumeMusic()
                    play?.setText("Pause")
                    appendEvent("Pressed Resume")
                }
            }
        }
    }



    var play: Button? = null
    var playEffect: Button? = null
    var music: TextView? = null

    var musicService: MusicService? = null
    var musicCompletionReceiver: MusicCompletionReceiver? = null
    var startMusicServiceIntent: Intent? = null
    var isInitialized = false
    var isBound = false

    private val musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {

            val binder = iBinder as MusicService.MyBinder
            musicService = binder.getService()
            isBound = true
            when (musicService?.getPlayingStatus()) {
                0 -> play?.setText("Start")
                1 -> play?.setText("Pause")
                2 -> play?.setText("Resume")
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            musicService = null
            isBound = false
        }
    }

    private fun appendEvent(event: String){

        WorkManager.getInstance().beginUniqueWork(TAG, ExistingWorkPolicy.KEEP, OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
            workDataOf("username" to USERNAME, "event" to event)
        )
            .build()).enqueue()
    }
    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(musicServiceConnection)
            isBound = false
        }
        unregisterReceiver(musicCompletionReceiver)

        appendEvent("onPause")
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE)
        }
        registerReceiver(musicCompletionReceiver, IntentFilter(MusicService.COMPLETE_INTENT))
        appendEvent("onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized)
        outState.putString(MUSIC_PLAYING, music?.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        play = findViewById(R.id.play)

        ///////////
        playEffect = findViewById(R.id.playeffect) as Button


        playEffect!!.setOnClickListener({

            musicService?.playEffect()
        })


        //////////



        music = findViewById(R.id.music)
        play?.setOnClickListener(this)

        //this restores the textview
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS)
            music?.text = savedInstanceState.getString(MUSIC_PLAYING)
        }
        startMusicServiceIntent = Intent(this, MusicService::class.java)
        if (!isInitialized) {
            startService(startMusicServiceIntent)
            isInitialized = true
        }
        musicCompletionReceiver = MusicCompletionReceiver(this)
    }

    fun updateName(musicName: String) {
        music?.text = "You are listening to " + musicName
        appendEvent("updateName: $musicName")

    }
}
