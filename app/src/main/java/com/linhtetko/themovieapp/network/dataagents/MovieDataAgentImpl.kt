package com.linhtetko.themovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.linhtetko.themovieapp.data.vos.ActorVO
import com.linhtetko.themovieapp.data.vos.GenreVO
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.network.responses.MovieListResponse
import com.linhtetko.themovieapp.utils.API_KEY
import com.linhtetko.themovieapp.utils.BASE_URL
import com.linhtetko.themovieapp.utils.GET_NOW_PLAYING
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MovieDataAgentImpl : MovieDataAgent {

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingTasking().execute()
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

    class GetNowPlayingTasking : AsyncTask<Unit, Unit, MovieListResponse?>() {

        override fun doInBackground(vararg p0: Unit?): MovieListResponse? {
            val url: URL
            var reader: BufferedReader? = null
            val stringBuilder: StringBuilder

            try {
                url =
                    URL("""${BASE_URL}${GET_NOW_PLAYING}?api_key=${API_KEY}&language=en-US&page=1""")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"

                connection.doInput = true
                connection.doOutput = false

                connection.readTimeout = 15 * 1000

                connection.connect()

                reader = BufferedReader(
                    InputStreamReader(connection.inputStream)
                )
                stringBuilder = StringBuilder()
                for (line in reader.readLines()) {
                    stringBuilder.append(line + "\n")
                }
                val respondString = stringBuilder.toString()
                Log.d("NowPlayingMovies", respondString)

                val response = Gson().fromJson(respondString, MovieListResponse::class.java)
                return response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("NowPlayingErr", e.message ?: "")
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (io: IOException) {
                        io.printStackTrace()
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }
    }
}