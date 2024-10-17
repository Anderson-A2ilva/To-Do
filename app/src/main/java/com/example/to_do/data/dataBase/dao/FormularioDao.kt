package com.example.to_do.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.to_do.data.model.Atividade
import kotlinx.coroutines.flow.Flow

@Dao
interface FormularioDao {
    @Query("SELECT * FROM atividade")
    fun buscaTodos(): Flow<List<Atividade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(atividade: Atividade)

    @Delete
    suspend fun delete(atividade: Atividade)
}