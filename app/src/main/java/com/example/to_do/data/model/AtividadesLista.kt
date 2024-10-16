package com.example.to_do.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AtividadesLista(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val cheked: Boolean = false,
    val nomeAtividade: String
)
