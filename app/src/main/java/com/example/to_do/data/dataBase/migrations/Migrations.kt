package com.example.to_do.data.dataBase.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `Atividade` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `data` TEXT NOT NULL, `hora` TEXT NOT NULL, `prioridade` TEXT NOT NULL, `categoria` TEXT NOT NULL, `atividadeText` TEXT NOT NULL)")
    }

}