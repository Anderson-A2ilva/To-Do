package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.dataBase.preferences.dataStore
import com.example.to_do.data.dataBase.preferences.usuarioLogadoPreferences
import com.example.to_do.databinding.ActivityLoginBinding
import com.example.to_do.extensions.toast
import com.example.to_do.extensions.vaiPara
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDataBase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        configuraBotaoLogin()
        configuraBotaoRegistrar()
    }

    private fun configuraBotaoLogin() {
        binding.loginBotaoLogin.setOnClickListener {
            val usuario = binding.textFieldLoginUser.editText?.text.toString()
            val senha = binding.textFieldLoginPassword.editText?.text.toString()
            autentica(usuario, senha)
        }
    }

    private fun autentica(usuario: String, senha: String) {
        lifecycleScope.launch {
            usuarioDao.autentica(usuario, senha)?.let { usuario ->
                dataStore.edit { preferences ->
                    preferences[usuarioLogadoPreferences] = usuario.id
                }
                if (usuario.id != null) {
                    vaiPara(MainActivity::class.java) {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                }
            } ?: toast("Usuário ou senha incorreto. Tente novamente")
        }
        if (usuario.isBlank() || senha.isBlank()){
            toast("Preencha todos os campos")
        }
    }

    private fun configuraBotaoRegistrar() {
        val botaoRegistrar = binding.loginBotaoRegistrar
        botaoRegistrar.setOnClickListener {
            vaiPara(Register::class.java)
        }
    }
}