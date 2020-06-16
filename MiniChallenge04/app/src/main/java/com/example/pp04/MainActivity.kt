package com.example.pp04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    val adapter = MovieListAdapter()

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        adapter.search(query)
        return true
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_movie)
        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                adapter.restore()
               return true
            }
        })


        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.restore_list ->{
//               //action to be performed
//            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.movie_list)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        model.allMovies.observe(
            this,
            Observer<List<MovieItem>>{ movies ->
                movies?.let{
                    adapter.setMovies(it)
                }
            }
        )

        (findViewById<Button>(R.id.refresh)).setOnClickListener{
            model.refreshMovies(1)
        }




        }








    inner class MovieListAdapter():
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){



        private var movies = emptyList<MovieItem>()

        private var moviesBackup= emptyList<MovieItem>()


        internal fun setMovies(movies: List<MovieItem>) {

            moviesBackup = movies
            this.movies = movies
            notifyDataSetChanged()
        }

        fun search(query: String?) {

            movies = movies.filter{it.title.contains(query!!)}

            notifyDataSetChanged()

        }

        fun restore(){

            movies = moviesBackup
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {

            return movies.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {


            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return MovieViewHolder(v)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


            //holder.bindItems(movieList[position])

            Glide.with(this@MainActivity).load(resources.getString(R.string.picture_base_url)+movies[position].poster_path).apply( RequestOptions().override(128, 128)).into(holder.view.findViewById(R.id.poster))

            holder.view.findViewById<TextView>(R.id.title).text=movies[position].title

            holder.view.findViewById<TextView>(R.id.rating).text=movies[position].vote_average.toString()


            holder.itemView.setOnClickListener(){


                // interact with the item

            }

        }



        inner class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

                if (view != null) {


                }


            }


        }
    }

}



