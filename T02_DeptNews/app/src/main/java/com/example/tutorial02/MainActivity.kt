package com.example.tutorial02

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showDetails(newsItem: NewsItem) {

        //ensure landscape
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            (supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment).displayNews(newsItem)
    }
}
