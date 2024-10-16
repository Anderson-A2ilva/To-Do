package com.example.to_do.converters

import androidx.room.TypeConverter
import com.example.to_do.data.model.AtividadesLista
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromAtividadesListaList(value: List<AtividadesLista>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toAtividadesListaList(value: String): List<AtividadesLista> {
        val listType = object : TypeToken<List<AtividadesLista>>() {}.type
        return Gson().fromJson(value, listType)
    }
}