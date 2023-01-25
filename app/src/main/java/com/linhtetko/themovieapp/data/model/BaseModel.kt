package com.linhtetko.themovieapp.data.model

import android.content.Context
import com.linhtetko.themovieapp.network.MovieApi
import com.linhtetko.themovieapp.persistance.MovieDatabase
import com.linhtetko.themovieapp.persistance.daos.MovieDAO
import com.linhtetko.themovieapp.utils.BASE_URL
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseModel {

    private var mMovieDatabase: MovieDatabase? = null
    protected var mMovieDao: MovieDAO? = null
    protected val mMovieApi: MovieApi

    init {

        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mMovieApi = retrofit.create(MovieApi::class.java)
    }

    fun initMovieDatabase(context: Context) {
        mMovieDatabase = MovieDatabase.getInstance(context)
        mMovieDao = mMovieDatabase?.movieDao()
    }
}