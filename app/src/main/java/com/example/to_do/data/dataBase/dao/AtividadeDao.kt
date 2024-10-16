package com.example.to_do.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.to_do.data.model.AtividadesLista

@Dao
interface AtividadeDao {
    //@Query("SELECT * FROM atividadeslista")
    //suspend fun getAllActivitys(): List<AtividadesLista>

//    @Insert
//    fun insertActivity(atividadesLista: AtividadesLista)
//
//    @Delete
//    fun deleteActivity(atividadesLista: AtividadesLista)
}