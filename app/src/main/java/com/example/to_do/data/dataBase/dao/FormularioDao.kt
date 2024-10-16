package com.example.to_do.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.to_do.data.model.Atividade

@Dao
interface FormularioDao {
    @Query("SELECT * FROM atividade")
    fun getAll(): List<Atividade>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(atividade: Atividade)

    @Delete
    suspend fun delete(atividade: Atividade)
}