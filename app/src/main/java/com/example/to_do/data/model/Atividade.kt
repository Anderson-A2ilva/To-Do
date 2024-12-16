package com.example.to_do.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Atividade(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    val nome: String,
    val data: String,
    val hora: String,
    val prioridade: String,
    val categoria: String,
    val atividadeText: String,
    var isConcluida: Boolean = false,
    val usuarioId: String? = null
) : Parcelable
