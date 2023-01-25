package com.linhtetko.themovieapp.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.linhtetko.themovieapp.data.vos.CollectionVO
import com.linhtetko.themovieapp.data.vos.ProductionCompaniesVO

class ProductionCompaniesTypeConverter {

    @TypeConverter
    fun toString(productionCompanies: List<ProductionCompaniesVO>?): String{
        return Gson().toJson(productionCompanies)
    }

    @TypeConverter
    fun toProductionCompanies(companies: String): List<ProductionCompaniesVO>?{
        val typeToken = object : TypeToken<List<ProductionCompaniesVO>?>(){}.type
        return Gson().fromJson(companies, typeToken)
    }
}