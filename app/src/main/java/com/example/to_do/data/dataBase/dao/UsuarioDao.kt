package com.example.to_do.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.to_do.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun salva(user: User)

    @Query("SELECT * FROM User WHERE id = :usuarioId AND  userPassword = :senha")
    suspend fun autentica(usuarioId: String, senha: String): User?

    @Query("SELECT * FROM User WHERE id = :usuarioId")
    fun buscaPorID(usuarioId: String): Flow<User>
}