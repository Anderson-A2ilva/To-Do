package com.example.to_do.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.to_do.R
import com.example.to_do.data.dataBase.preferences.dataStore
import com.example.to_do.data.dataBase.preferences.usuarioLogadoPreferences
import com.example.to_do.databinding.ActivityUsuarioBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class Usuario : UsuarioBaseActivity() {
    private val binding by lazy {
        ActivityUsuarioBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoDeslogar()
        preencheNome()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun configuraBotaoDeslogar() {
        val deslogarBotao = binding.usuarioLogadoBotaoDeslogar
        deslogarBotao.setOnClickListener {
            lifecycleScope.launch {
                deslogarUsuario()
            }
        }
    }

    private fun preencheNome() {
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect { usuarioLogado ->
                    binding.usuarioLogadoNome.text = usuarioLogado.id
                }
        }
    }
}