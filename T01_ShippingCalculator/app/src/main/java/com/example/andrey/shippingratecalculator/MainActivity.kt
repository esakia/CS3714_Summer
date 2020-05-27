package com.example.andrey.shippingratecalculator

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Math.round

class MainActivity : AppCompatActivity() {

    var usdSeekBar:SeekBar? = null
    var lbsSeekBar:SeekBar?= null
    var usdTotal:TextView?= null
    var lbsTotal:TextView?= null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            usdSeekBar = findViewById(R.id.usd_seek_bar)
            lbsSeekBar = findViewById(R.id.lbs_seek_bar)
            usdTotal =   findViewById(R.id.usd_total)
            lbsTotal =   findViewById(R.id.lbs_total)


            setBoundaries()






        usdSeekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            var usd_sync=0

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //progress changed
                usdTotal?.text = "USD:$progress"
                usd_sync=progress

                findViewById<TextView>(R.id.total).text = "Total cost:"+progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
               //when the change starts
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(usd_sync.toFloat()<10*1.75)
                    lbsSeekBar?.progress = round(usd_sync/1.75f)
                else
                    lbsSeekBar?.progress = round(usd_sync/1.25f)
            }
        })

        lbsSeekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            var lbs_sync=0

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //progress changed

                lbsTotal?.text = "lbs:$progress"
                lbs_sync=progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(lbs_sync<10)
                    usdSeekBar?.progress = round(lbs_sync*1.75f)
                else
                    usdSeekBar?.progress = round(lbs_sync*1.25f)
            }
        })

    }


    private fun setBoundaries(){

        usdSeekBar?.min = 1

        usdSeekBar?.max = 125

        lbsSeekBar?.min = 1

        lbsSeekBar?.max = 100

    }



}
