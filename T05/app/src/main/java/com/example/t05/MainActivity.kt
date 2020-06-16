package com.example.t05

import android.annotation.SuppressLint
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.os.IBinder
import android.view.View


class MainActivity : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        if (isBound) {
            when (musicService?.getPlayingStatus()) {
                0 -> {
                    musicService?.startMusic()
                    play?.text = "Pause"
                }
                1 -> {
                    musicService?.pauseMusic()
                    play?.text = "Resume"
                }
                2 -> {
                    musicService?.resumeMusic()
                    play?.text = "Pause"
                }
            }
        }
    }

    val INITIALIZE_STATUS = "intialization status"
    val MUSIC_PLAYING = "music playing"

    var play: Button? = null
    var music: TextView? = null

    var musicService: MusicService? = null
    var musicCompletionReceiver: MusicCompletionReceiver? = null
    var startMusicServiceIntent: Intent? = null
    var isInitialized = false
    var isBound = false

    private val musicServiceConnection = object : ServiceConnection {
        @SuppressLint("SetTextI18n")
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {

            val binder = iBinder as MusicService.MyBinder
            musicService = binder.getService()
            isBound = true
            when (musicService?.getPlayingStatus()) {
                0 -> play?.text = "Start"
                1 -> play?.text = "Pause"
                2 -> play?.text = "Resume"
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            musicService = null
            isBound = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(musicServiceConnection)
            isBound = false
        }
        unregisterReceiver(musicCompletionReceiver)
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE)
        }
        registerReceiver(musicCompletionReceiver, IntentFilter(MusicService.COMPLETE_INTENT))
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

    @SuppressLint("SetTextI18n")
    fun updateName(musicName: String) {
        music?.text = "You are listening to $musicName"
    }
}
