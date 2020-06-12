package com.example.tutorial02


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient


/**
 * A simple [Fragment] subclass.
 *
 */
class WikiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_wiki, container, false)

        val webView =  (view.findViewById(R.id.wikipedia) as WebView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(this.arguments?.getString("url"))
        return view
    }


}
