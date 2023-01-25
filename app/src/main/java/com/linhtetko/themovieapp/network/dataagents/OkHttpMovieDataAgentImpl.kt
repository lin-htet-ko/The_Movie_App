package com.linhtetko.themovieapp.network.dataagents

import android.os.AsyncTask
import com.google.gson.Gson
import com.linhtetko.themovieapp.data.vos.ActorVO
import com.linhtetko.themovieapp.data.vos.GenreVO
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.network.responses.MovieListResponse
import com.linhtetko.themovieapp.utils.API_KEY
import com.linhtetko.themovieapp.utils.BASE_URL
import com.linhtetko.themovieapp.utils.GET_NOW_PLAYING
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object OkHttpMovieDataAgentImpl : MovieDataAgent {

    private val client = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .callTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS).build()


    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieOkHttpTask(client).execute()
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
    }

    override fun getGenre(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMovieListByGenre(
        genreId: Int,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getActorList(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMovieDetail(
        movieId: Int,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    override fun getMovieCredit(
        movieId: Int,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {

    }

    class GetNowPlayingMovieOkHttpTask(
        private val okHttpClient: OkHttpClient
    ) : AsyncTask<Unit, Unit, MovieListResponse?>() {

        override fun doInBackground(vararg p0: Unit?): MovieListResponse? {
            val request = Request.Builder()
                .url("""$BASE_URL$GET_NOW_PLAYING?api_key=$API_KEY&language=en-US&page=1""").build()

            try {
                val response = okHttpClient.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.let {
                        val responseString = it.string()
                        val response =
                            Gson().fromJson(responseString, MovieListResponse::class.java)
                        return response
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    }
}