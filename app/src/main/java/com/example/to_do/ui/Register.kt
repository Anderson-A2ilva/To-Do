package com.example.to_do.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.User
import com.example.to_do.databinding.ActivityRegisterBinding
import com.example.to_do.extensions.toast
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDataBase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.registrarBotaoRegistrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            if (novoUsuario != null) {
                lifecycleScope.launch {
                    try {
                        dao.salva(novoUsuario)
                        toast("Usuário cadastrado com sucesso")
                        finish()
                    } catch (e: Exception) {
                        Log.e("salvaNovoUsuario", "configuraBotaoCadastrar: ", e)
                        toast("Falha ao cadastrar usuário")
                    }
                }
            } else {
                toast("Não foi possivel concluir seu cadastro. Verifique os campos")
            }
        }
    }

    private fun criaUsuario(): User? {
        val usuario = binding.textFieldRegistrarNomeUsuarioText.text.toString()
        val senha = binding.textFieldRegistrarSenhaText.text.toString()
        val confirmarSenha = binding.textFieldRegistrarConfirmarSenhaText.text.toString()

        if (confirmarSenha != senha) {
            toast("Senhas diferente. Tente novamente")
            return null
        }

        if (usuario.isBlank() || senha.isBlank()) {
            toast("Preencha todos os campos.")
            return null
        }

        return User(usuario, senha, confirmarSenha)
    }
}