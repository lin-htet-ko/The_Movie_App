package com.linhtetko.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.MovieListViewHolderDelegate
import com.linhtetko.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie_view.view.*

class MovieViewHolder(itemView: View, delegate: MovieListViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovieVO: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovieVO?.let { movie -> delegate.onMovieItemClick(movie.id) }
        }
    }


    fun bindData(movieVO: MovieVO) {
        mMovieVO = movieVO

        Glide.with(itemView.context).load("$IMAGE_BASE_URL${movieVO.posterPath}")
            .into(itemView.ivMovieImage)
        itemView.apply {
            tvMovieName.text = movieVO.title
            tvMovieRating.text = movieVO.voteAverage?.toString()
            rbMovieRating.rating = movieVO.setRatingBaseOnFiveStar()
        }
    }
}