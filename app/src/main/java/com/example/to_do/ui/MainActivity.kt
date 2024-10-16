package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val formularioDao by lazy {
        AppDataBase.instancia(this).formularioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        lifecycleScope.launch {
//            formularioDao.getAll().collect { formularioDao ->}
//        }
        //recyclerviewConfigura()
        fabConfigura()
    }

    private fun fabConfigura() {
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }
    }

    private fun recyclerviewConfigura() {
        TODO("Not yet implemented")
    }
}