package com.example.to_do.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.Atividade
import com.example.to_do.databinding.ActivityAtividadeInformacoesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtividadeInformacoes: AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private var atividadeId: Long = 0L
    private val binding by lazy {
        ActivityAtividadeInformacoesBinding.inflate(layoutInflater)
    }
    private val formularioDao by lazy {
        AppDataBase.instancia(this).formularioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        atividadeId = intent.getLongExtra(CHAVE_ATIVIDADE_ID, 0L)
        Log.d("AtividadeInformacoes", "Atividade ID recebido: $atividadeId")
        if (atividadeId != 0L){
            tentaCarregar()
        }
    }

    private fun tentaCarregar(){
        scope.launch {
            val carregaFormulario = withContext(Dispatchers.IO) {
                formularioDao.buscaPorId(atividadeId).firstOrNull()
            }
            carregaFormulario?.let {
                preencheComDados(it)
            }
        }
    }

    private fun preencheComDados (atividade: Atividade){
        with(binding){
            atividadesInformacoesNome.text = atividade.nome.toString()
            atividadesInformacoesData.text = "data: ${atividade.data.toString()}"
            atividadesInformacoesPrioridade.text = "Príoridade: ${atividade.prioridade.toString()}"
        }
    }
}
