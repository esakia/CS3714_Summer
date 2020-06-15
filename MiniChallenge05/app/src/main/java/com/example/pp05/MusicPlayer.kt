package com.example.pp05
import android.media.AudioAttributes
import android.media.MediaPlayer
import java.io.IOException
import java.util.*


class MusicPlayer(val musicService: MusicService): MediaPlayer.OnCompletionListener {

    val MUSICPATH = arrayOf(R.raw.mario, R.raw.tetris)

    val MUSICNAME = arrayOf("Super Mario", "Tetris")

    val EFFECTPATH = arrayOf(R.raw.clapping, R.raw.cheering, R.raw.lestgohokies)

    private var effectTimer: Timer? = null


    lateinit var player: MediaPlayer
    lateinit var effect: MediaPlayer
    var currentPosition = 0
    var musicIndex = 0
    private var musicStatus = 0//0: before starts 1: playing 2: paused

    fun getMusicStatus(): Int {
        return musicStatus
    }

    fun getMusicName(): String {
        return MUSICNAME[musicIndex]
    }



    fun playRandomEffectDelayed() {

        effectTimer = Timer()
        effectTimer?.schedule(object : TimerTask() {
            override fun run() {

                effect = MediaPlayer.create(musicService.applicationContext, EFFECTPATH[(0..(EFFECTPATH.size-1)).random()])

                effect.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                try {

                    effect.start()

                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        }, 500, 10000) //half second delay, random effect every 10 seconds

    }

    fun playMusic() {

        player = MediaPlayer.create(musicService.applicationContext, MUSICPATH[musicIndex])
        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        try {
            player.setOnCompletionListener(this)
            player.start()
            musicService.onUpdateMusicName(getMusicName())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        musicStatus = 1
    }

    fun pauseMusic() {
        if (player.isPlaying()) {
            player.pause()
            currentPosition = player.getCurrentPosition()
            musicStatus = 2
        }
    }

    fun resumeMusic() {
        player.seekTo(currentPosition)
        player.start()
        musicStatus = 1
    }


    override fun onCompletion(mp: MediaPlayer?) {
        musicIndex = (musicIndex + 1) % 2
        player.release()
        playMusic()
    }




}