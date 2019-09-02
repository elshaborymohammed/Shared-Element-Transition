package com.smart.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.smart.sample.MainActivity
import com.smart.sample.R
import com.smart.sample.adapter.MovieAdapter
import com.smart.sample.bean.Movie
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/**
 * A simple [Fragment] subclass.
 */
class FragmentMovieList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        movieList.let {
            val fromJson = Gson().fromJson<Array<Movie>>(readMovieJson(), Array<Movie>::class.java)
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = MovieAdapter(fromJson, this@FragmentMovieList)
        }
    }

    /**
     * function to read the dummy json
     *
     * @return json string of movie list
     */
    private fun readMovieJson(): String {
        val buf = StringBuilder()
        val json: InputStream
        try {
            json = context!!.assets.open("movies.json")
            val bufferedReader = BufferedReader(InputStreamReader(json, StandardCharsets.UTF_8))
            bufferedReader.readLines().forEach { buf.append(it) }
            bufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return buf.toString()
    }

    /**
     * function to open the movie detail fragment
     *
     * @param position Movie list position
     * @param movie Selected movie from movies list
     */
    fun openMovieDetailFragment(position: Int, movie: Movie, view: View) {
        if (activity is MainActivity) {

            val bundle = Bundle()
            bundle.putString("transitionName", "transition$position")
            bundle.putSerializable("movie", movie)

            val extras = FragmentNavigator.Extras.Builder()
                    .addSharedElement(view, "transition$position")
                    .build()

            Navigation.findNavController(view).navigate(R.id.action_to_movie_detail, bundle, null, extras)
        }
    }

}
