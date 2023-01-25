package com.linhtetko.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.linhtetko.themovieapp.viewholders.ShowCaseViewHolder

class ShowCaseAdapter(
    private val showCaseDelegate: ShowCaseViewHolderDelegate
) : RecyclerView.Adapter<ShowCaseViewHolder>() {

    private var list: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_showcases, parent, false)
        return ShowCaseViewHolder(view, showCaseDelegate)
    }

    override fun onBindViewHolder(holder: ShowCaseViewHolder, position: Int) {
        if (list.isNotEmpty()){
            holder.bindData(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(items: List<MovieVO>) {
        list = items
        notifyDataSetChanged()
    }
}