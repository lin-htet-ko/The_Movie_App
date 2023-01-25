package com.linhtetko.themovieapp.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.linhtetko.themovieapp.data.vos.CollectionVO
import com.linhtetko.themovieapp.data.vos.ProductionCompaniesVO
import com.linhtetko.themovieapp.data.vos.ProductionCountriesVO

class ProductionCountriesTypeConverter {

    @TypeConverter
    fun toString(productionCountries: List<ProductionCountriesVO>?): String{
        return Gson().toJson(productionCountries)
    }

    @TypeConverter
    fun toProductionCountries(countries: String): List<ProductionCountriesVO>?{
        val typeToken = object : TypeToken<List<ProductionCountriesVO>?>(){}.type
        return Gson().fromJson(countries, typeToken)
    }
}