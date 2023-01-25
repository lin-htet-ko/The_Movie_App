package com.linhtetko.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.linhtetko.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_showcases.view.*

class ShowCaseViewHolder(itemView: View, showCaseDelegate: ShowCaseViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie -> showCaseDelegate.onShowCaseClick(movie.id) }
        }
    }

    fun bindData(movieVO: MovieVO) {
        mMovie = movieVO

        itemView.apply {
            Glide.with(context).load("$IMAGE_BASE_URL${movieVO.posterPath}").into(ivShowCase)
            tvMovieShowCaseName.text = movieVO.title
            tvMovieShowCaseDate.text = movieVO.releaseDate
        }
    }
}