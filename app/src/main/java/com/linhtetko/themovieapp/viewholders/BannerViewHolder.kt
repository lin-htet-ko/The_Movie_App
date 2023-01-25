package com.linhtetko.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.BannerViewHolderDelegate
import com.linhtetko.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_item_banner.view.*

class BannerViewHolder(itemView: View, delegate: BannerViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movieVO -> delegate.onItemClick(movieVO.id) }
        }
    }

    fun bindData(movieVO: MovieVO) {

        mMovie = movieVO

        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${movieVO.posterPath}")
            .into(itemView.ivBannerImage)

        itemView.tvBannerMovieName.text = movieVO.title
    }
}