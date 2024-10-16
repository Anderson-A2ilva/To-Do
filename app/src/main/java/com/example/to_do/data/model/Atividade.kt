package com.example.to_do.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Atividade(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val data: String,
    val prioridade: String,
    val atividadeList: List<AtividadesLista>
)
