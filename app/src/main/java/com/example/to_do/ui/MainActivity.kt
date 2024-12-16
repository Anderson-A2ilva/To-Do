package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do.R
import com.example.to_do.adapter.AdapterRecyclerView
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.databinding.ActivityMainBinding
import com.example.to_do.extensions.vaiPara
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : UsuarioBaseActivity() {

    private val scope = MainScope()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        AdapterRecyclerView(
            this,
            emptyList()
        )
    }
    private val formularioDao by lazy {
        AppDataBase.instancia(this).formularioDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        fabConfigura()
        recyclerviewConfigura()
        supportActionBar?.title = "To-do"

        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
                        buscaAtividadesUsuario(usuario.id)
                    }
            }
        }
    }

    private suspend fun buscaAtividadesUsuario(usuarioId: String) {
        formularioDao.buscaTodosDoUsuario(usuarioId).collect { atividades ->
            adapter.atualiza(atividades)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home_configuracao -> {
                vaiPara(Usuario::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun fabConfigura() {
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            vaiPara(Formulario::class.java)
        }
    }

    private fun recyclerviewConfigura() {
        val recyclerView = binding.MainActivityRecyclerView
        recyclerView.adapter = adapter

        adapter.clickInformacoes = { atividade ->
            val dialog = AtividadeDialog.newInstance(atividade)
            dialog.show(supportFragmentManager, "AtividadeDialog")
        }

        adapter.quandoClicarEmRemover = { atividade ->
            scope.launch {
                withContext(Dispatchers.IO) {
                    formularioDao.delete(atividade)
                }
                val atividadesAtualizadas =
                    formularioDao.buscaTodos().first()  // Obtém a nova lista
                withContext(Dispatchers.Main) {
                    adapter.atualiza(atividadesAtualizadas)
                }
            }
        }

        adapter.quandoClicarEmEditar = { atividade ->
            val intent = Intent(this, Formulario::class.java).apply {
                putExtra(CHAVE_ATIVIDADE_ID, atividade.id)
                putExtra("ACTION_TYPE", "EDITAR")
            }
            startActivity(intent)
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}