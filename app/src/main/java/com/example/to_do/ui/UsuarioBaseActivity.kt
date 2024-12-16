package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.dataBase.preferences.dataStore
import com.example.to_do.data.dataBase.preferences.usuarioLogadoPreferences
import com.example.to_do.data.model.User
import com.example.to_do.extensions.vaiPara
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {
    private val usuarioDao by lazy {
        AppDataBase.instancia(this).usuarioDao()
    }

    private val _usuario: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val usuario: MutableStateFlow<User?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun buscaUsuario(usuarioId: String) : User? {
        return usuarioDao
            .buscaPorID(usuarioId)
            .firstOrNull().also {
                _usuario.value = it
            }
    }

    protected suspend fun deslogarUsuario(){
        dataStore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)
        }
        vaiparaLogin()
    }

    private fun vaiparaLogin() {
        vaiPara(Login::class.java){
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        finish()
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiparaLogin()
        }
    }
}