package com.linhtetko.themovieapp.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.persistance.daos.MovieDAO

@Database(
    entities = [
        MovieVO::class
    ],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    companion object {

        private var INSTANCE: MovieDatabase? = null
        private const val MOVIE_DATABASE = "movies_database"

        fun getInstance(context: Context): MovieDatabase? {
            when (INSTANCE) {
                null -> {
                    INSTANCE =
                        Room.databaseBuilder(context, MovieDatabase::class.java, MOVIE_DATABASE)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun movieDao(): MovieDAO
}