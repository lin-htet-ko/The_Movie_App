package com.linhtetko.themovieapp.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.linhtetko.themovieapp.data.vos.CollectionVO
import com.linhtetko.themovieapp.data.vos.GenreVO

class GenreListTypeConverter {

    @TypeConverter
    fun toString(genres: List<GenreVO>?): String{
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenres(genresString: String): List<GenreVO>?{
        val typeToken = object : TypeToken<List<GenreVO>?>(){}.type
        return Gson().fromJson(genresString, typeToken)
    }
}