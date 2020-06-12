package com.example.tutorial02



import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    //current dept
    var dept:String? = null
    var news:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        dept = this.arguments?.getString("dept")
        news =  this.arguments?.getString("news")


        (view.findViewById(R.id.dept) as TextView).text = dept
        (view.findViewById(R.id.news) as TextView).text = news


        val resId = resources.getIdentifier(dept+"url", "string", context?.packageName)
        //opens next fragment with webview
        (view.findViewById(R.id.news) as TextView).setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    v?.findNavController()?.navigate(R.id.action_detailFragment_to_wikiFragment,
                        bundleOf("url" to resources.getString(resId))
                    )
            }
        })
    }

    fun displayNews(news: NewsItem){


        (view?.findViewById(R.id.dept) as TextView).text = news.dept

        (view?.findViewById(R.id.news) as TextView).text = news.content

    }

}
