package com.example.to_do.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_do.data.dataBase.dao.FormularioDao
import com.example.to_do.data.dataBase.dao.UsuarioDao
import com.example.to_do.data.dataBase.migrations.MIGRATION_3_4
import com.example.to_do.data.dataBase.migrations.MIGRATION_4_5
import com.example.to_do.data.model.Atividade
import com.example.to_do.data.model.User

@Database(
    entities = [Atividade::class, User::class],
    version = 5,
    exportSchema = true
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun formularioDao(): FormularioDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: AppDataBase? = null
        fun instancia(context: Context): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "atividade.db"
            ).addMigrations(
                MIGRATION_3_4,
                MIGRATION_4_5
            ).build()
                .also {
                    db = it
                }
        }
    }
}