package com.smart.sample.adapter

import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.smart.sample.R
import com.smart.sample.bean.Movie
import com.smart.sample.fragments.FragmentMovieList
import com.squareup.picasso.Picasso

/**
 * Author: Elshabory
 */
class MovieAdapter(private val movieList: Array<Movie>, private val fragment: FragmentMovieList) : RecyclerView.Adapter<MovieAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.card_item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = movieList[position]
        holder.position = position

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.movieImage.transitionName = "transition$position"
        }

        if (!TextUtils.isEmpty(movie.image)) {
            Picasso.get().load(movie.image).into(holder.movieImage)
        } else {
            holder.movieImage.setImageDrawable(null)
        }

        holder.movieName.text = movie.title
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        internal val movieName: TextView = itemView.findViewById(R.id.movieName)
        internal var position = -1

        init {
            itemView.setOnClickListener { v -> fragment.openMovieDetailFragment(adapterPosition, movieList[position], v.findViewById(R.id.movieImage)) }
        }
    }
}
