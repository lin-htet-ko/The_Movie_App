package com.linhtetko.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.adapters.MovieListAdapter
import com.linhtetko.themovieapp.data.model.MovieModel
import com.linhtetko.themovieapp.data.model.MovieModelImpl
import com.linhtetko.themovieapp.delegates.MovieListViewHolderDelegate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity(R.layout.activity_movie_search),
    MovieListViewHolderDelegate {

    companion object{
        fun newIntent(context: Context) = Intent(context, MovieSearchActivity::class.java)
    }
    private val mMovieModel: MovieModel = MovieModelImpl
    private lateinit var mMovieAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpRecyclerView()
        textChangeListener()
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieListAdapter(this)
        rvSearch.adapter = mMovieAdapter
    }

    private fun textChangeListener() {
        tiSearch.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .flatMap { mMovieModel.getSearchedMovies(it.toString()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            }, {
                showError(it.localizedMessage ?: "")
            })
    }

    private fun showError(text: String) {
        Snackbar.make(clSearch, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onMovieItemClick(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }


}