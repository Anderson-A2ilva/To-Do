package com.example.to_do.data.dataBase.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessao-usuario")
val usuarioLogadoPreferences = stringPreferencesKey("usuarioLogado")