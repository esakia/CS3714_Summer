package com.example.tutorial03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {


    private var coroutineJob: Job? = null
    private val delay: Long=1_000
    private val repetitions = 10



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start
        (findViewById<Button>(R.id.start)).setOnClickListener{
            slotMachineDraw((findViewById(R.id.left)),
                (findViewById(R.id.middle)),
                (findViewById(R.id.right)))
        }

        //stop
        (findViewById<Button>(R.id.stop)).setOnClickListener{
            coroutineJob?.cancel()
        }

    }

    private val TAG = "slotmachine"

    //return a random number between 0 to 10
    suspend fun randomInt(repetitions: Int, delay: Long, v: TextView): Int {
        var rand = -1
        for(i: Int in 0..repetitions){

            delay(delay)

            rand = (0..10).random()

            Log.d(TAG, "Background -> "+Thread.currentThread().name)

            //updating the UI from the main thread
            withContext(Dispatchers.Main){

                Log.d(TAG, "UI -> "+Thread.currentThread().name)
                v.text = rand.toString()
            }

        }
        return rand
    }

    fun slotMachineDraw(left: TextView, middle: TextView, right: TextView)  {
        //Cancel the job object in case user wants to start it again
        coroutineJob?.cancel()
        //Launch returns a Job object that we can then use to cancel the coroutine
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {

            // 'async' will let us run the randInt generators concurrently.
            // notice the parameters that we are passing to randomInt()
            val leftDeffered = async {
                randomInt(repetitions,delay, left)
            }

            val middleDeferred = async {
                randomInt(repetitions,delay, middle)
            }

            val rightDeferred = async {
                randomInt(repetitions,delay, right)
            }

            // 'await' is needed to retrieve the final result once it is available
            var leftFinal = leftDeffered.await()
            val middleFinal = middleDeferred.await()
            val rightFinal = rightDeferred.await()




            // we can display the final values with hyphens on both ends
            withContext(Dispatchers.Main) {

                Log.d(TAG, "Final values UI -->"+Thread.currentThread().name)

                left.text = "-$leftFinal-"
                middle.text = "-$middleFinal-"
                right.text = "-$rightFinal-"
            }


        }

    }



    override fun onDestroy() {
        super.onDestroy()

        coroutineJob?.cancel()
    }


}

