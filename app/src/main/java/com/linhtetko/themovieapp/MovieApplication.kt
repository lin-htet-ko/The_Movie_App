package com.linhtetko.themovieapp

import android.app.Application
import com.linhtetko.themovieapp.data.model.MovieModelImpl
import com.linhtetko.themovieapp.persistance.MovieDatabase

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        MovieModelImpl.initMovieDatabase(applicationContext)
    }
}