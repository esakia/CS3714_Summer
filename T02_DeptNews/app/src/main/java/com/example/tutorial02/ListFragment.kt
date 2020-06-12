package com.example.tutorial02


import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView




/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val newsItems = ArrayList<NewsItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initArray(newsItems)

        var view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        // This tells the recyclerview that we want to show our data items in a vertical list. We could do a horizontal list,
        // a grid or even something custom in order to display the data items.
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewAdapter = RecyclerViewAdapter(newsItems, activity as MainActivity)

        recyclerView.adapter = viewAdapter

        return view
    }

    private fun initArray(myDataset: MutableList<NewsItem>){
        myDataset.clear()

        myDataset.add(NewsItem("CS", Color.GRAY, getString(R.string.CS)))
        myDataset.add(NewsItem("ECE",Color.YELLOW,getString(R.string.ECE)))
        myDataset.add(NewsItem("MATH",Color.WHITE,getString(R.string.MATH)))
        myDataset.add(NewsItem("STAT",Color.MAGENTA, getString(R.string.STAT)))
        myDataset.add(NewsItem("PHYS",Color.RED,getString(R.string.PHYS)))

    }



}

class RecyclerViewAdapter(private val myDataset: ArrayList<NewsItem>, private val activity: MainActivity) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerViewAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerviewitem, parent, false)


        return ViewHolder(v, activity)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(myDataset[position])

    }

    class ViewHolder(private val view: View, private val activity: MainActivity) : RecyclerView.ViewHolder(view){
        fun bindItems(newsItem: NewsItem) {
            val dept: TextView = itemView.findViewById(R.id.dept)
            val news:TextView = itemView.findViewById(R.id.news)
            itemView.setBackgroundColor(newsItem.color)


            dept.text = newsItem.dept
            news.text = newsItem.content.substring(0,50)

            itemView.setOnClickListener {

                // portrait
                if(activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                     view.findNavController().navigate(R.id.action_listFragment_to_detailFragment,
                         bundleOf("dept" to newsItem.dept, "news" to newsItem.content))
                // landscape
                else
                    activity.showDetails(newsItem)
            }
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}
