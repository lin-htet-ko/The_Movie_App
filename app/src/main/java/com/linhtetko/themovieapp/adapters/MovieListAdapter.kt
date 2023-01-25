package com.linhtetko.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.MovieListViewHolderDelegate
import com.linhtetko.themovieapp.viewholders.MovieViewHolder

class MovieListAdapter(
    private val delegate: MovieListViewHolderDelegate
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var list: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_view, parent, false)
        return MovieViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (list.isNotEmpty()){
            holder.bindData(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(items: List<MovieVO>){
        list = items
        notifyDataSetChanged()
    }
}