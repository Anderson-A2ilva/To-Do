package com.example.to_do.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_do.data.dataBase.dao.FormularioDao
import com.example.to_do.data.dataBase.migrations.MIGRATION_1_2
import com.example.to_do.data.model.Atividade
import com.example.to_do.ui.AtividadeDialog

@Database(
    entities = [Atividade::class],
    version = 2,
    exportSchema = true
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun formularioDao(): FormularioDao

    companion object {
        @Volatile
        private var db: AppDataBase? = null
        fun instancia(context: AtividadeDialog): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "atividade.db"
            ).addMigrations(MIGRATION_1_2).build()
                .also {
                    db = it
                }
        }
    }
}