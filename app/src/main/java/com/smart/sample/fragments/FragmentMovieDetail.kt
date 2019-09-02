package com.smart.sample.fragments


import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.smart.sample.R
import com.smart.sample.bean.Movie
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class FragmentMovieDetail : Fragment() {
    private var movieImage: ImageView? = null
    private var movieName: TextView? = null
    private var movieRating: TextView? = null
    private var movieYear: TextView? = null
    private var movieGenre: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieImage = view.findViewById(R.id.movieImage)
        movieName = view.findViewById(R.id.movieName)
        movieRating = view.findViewById(R.id.movieRating)
        movieYear = view.findViewById(R.id.movieYear)
        movieGenre = view.findViewById(R.id.movieGenre)

        val b = arguments
        if (b != null) {
            val transitionName = b.getString("transitionName")
            val movie = b.getSerializable("movie") as Movie

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movieImage!!.transitionName = transitionName
            }

            if (movie != null) {
                if (!TextUtils.isEmpty(movie.image)) {
                    Picasso.get().load(movie.image).into(movieImage)
                } else
                    movieImage!!.setImageDrawable(null)

                movieName!!.text = movie.title
                movieRating!!.text = movie.rating.toString()
                movieYear!!.text = movie.releaseYear.toString()
                movieGenre!!.text = movie.genre.toString()
            }
        }
    }
}// Required empty public constructor
