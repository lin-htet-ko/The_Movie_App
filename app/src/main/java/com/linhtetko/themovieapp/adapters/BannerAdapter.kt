package com.linhtetko.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.BannerViewHolderDelegate
import com.linhtetko.themovieapp.viewholders.BannerViewHolder

class BannerAdapter(private val bannerDelegate: BannerViewHolderDelegate) :
    RecyclerView.Adapter<BannerViewHolder>() {

    private var mMovieList: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_banner, parent, false)
        return BannerViewHolder(view, bannerDelegate)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        if(mMovieList.isNotEmpty()){
            holder.bindData(mMovieList[position])
        }
    }


    override fun getItemCount(): Int {
        return if (mMovieList.size > 5)
            5
        else
            mMovieList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(items: List<MovieVO>) {
        mMovieList = items
        notifyDataSetChanged()
    }
}