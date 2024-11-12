package com.example.to_do.data.dataBase.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS `Atividade` (
            |`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            | `nome` TEXT NOT NULL,
            |  `data` TEXT NOT NULL,
            |   `hora` TEXT NOT NULL,
            |    `prioridade` TEXT NOT NULL,
            |     `categoria` TEXT NOT NULL,
            |      `atividadeText` TEXT NOT NULL)
            |      """.trimMargin()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `Atividade` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `nome` TEXT NOT NULL,
        `data` TEXT NOT NULL,
        `hora` TEXT NOT NULL,
        `prioridade` TEXT NOT NULL,
        `categoria` TEXT NOT NULL,
        `atividadeText` TEXT NOT NULL
    )
""".trimIndent()
        )

        db.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `Usuario` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `user` TEXT NOT NULL,
        `userName` TEXT NOT NULL,
        `userPassword` TEXT NOT NULL
    )
""".trimIndent()
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `Atividade` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `nome` TEXT NOT NULL,
        `data` TEXT NOT NULL,
        `hora` TEXT NOT NULL,
        `prioridade` TEXT NOT NULL,
        `categoria` TEXT NOT NULL,
        `atividadeText` TEXT NOT NULL
    )
""".trimIndent()
        )
        db.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` TEXT NOT NULL, `userName` TEXT NOT NULL, `userPassword` TEXT NOT NULL, PRIMARY KEY(`id`))")
    }
}

val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Atividade ADD COLUMN Convert to `usuarioId` TEXT")
    }

}