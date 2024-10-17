package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do.adapter.AdapterRecyclerView
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
//        lifecycleScope.launch {
//            formularioDao.buscaTodos().collect {atividades ->
//                Log.d("MainActivity", "Atividades atualizadas: $atividades")
//                adapter.atualiza(atividades)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            formularioDao.buscaTodos().collect { atividades ->
                adapter.atualiza(atividades)
            }
        }
    }

    private fun fabConfigura() {
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }
    }

    private fun recyclerviewConfigura() {
        val recyclerView = binding.MainActivityRecyclerView
        recyclerView.adapter = adapter
//        adapter.atualiza(formularioDao.getAll())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}