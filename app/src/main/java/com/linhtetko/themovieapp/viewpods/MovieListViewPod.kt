package com.linhtetko.themovieapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.linhtetko.themovieapp.adapters.MovieListAdapter
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.delegates.MovieListViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    lateinit var mMovieAdapter : MovieListAdapter
    lateinit var mDelegate: MovieListViewHolderDelegate

    override fun onFinishInflate() {
//        setMovieRecyclerView()
        super.onFinishInflate()
    }

    fun setUpMovieListViewPod(delegate: MovieListViewHolderDelegate){
        setDelegate(delegate)
        setMovieRecyclerView()
    }

    private fun setDelegate(delegate: MovieListViewHolderDelegate){
        mDelegate = delegate
    }

    private fun setMovieRecyclerView() {
        mMovieAdapter = MovieListAdapter(mDelegate)
        rvMovieList.adapter = mMovieAdapter
    }

    fun setNewData(items: List<MovieVO>){
        mMovieAdapter.setNewData(items)
    }
}