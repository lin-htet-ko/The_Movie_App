package com.linhtetko.themovieapp.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.linhtetko.themovieapp.data.vos.CollectionVO
import com.linhtetko.themovieapp.data.vos.ProductionCompaniesVO
import com.linhtetko.themovieapp.data.vos.SpokenLanguagesVO

class SpokenLanguageTypeConverter {

    @TypeConverter
    fun toString(spokenLanguages: List<SpokenLanguagesVO>?): String{
        return Gson().toJson(spokenLanguages)
    }

    @TypeConverter
    fun toSpokenLanguages(spokenLanguagesVO: String): List<SpokenLanguagesVO>?{
        val typeToken = object : TypeToken<List<SpokenLanguagesVO>?>(){}.type
        return Gson().fromJson(spokenLanguagesVO, typeToken)
    }
}