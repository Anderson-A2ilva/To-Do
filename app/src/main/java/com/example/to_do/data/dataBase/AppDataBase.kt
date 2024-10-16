package com.example.to_do.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do.converters.Converters
import com.example.to_do.data.dataBase.dao.AtividadeDao
import com.example.to_do.data.dataBase.dao.FormularioDao
import com.example.to_do.data.model.Atividade

@Database(
    entities = [Atividade::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun formularioDao(): FormularioDao
    abstract fun atividadeListaDao(): AtividadeDao

    companion object {
        @Volatile
        private var db: AppDataBase? = null
        fun instancia(context: Context): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "atividade.db"
            ).build()
                .also {
                    db = it
                }
        }
    }
}