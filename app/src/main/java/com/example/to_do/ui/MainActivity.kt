package com.example.to_do.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recyclerviewConfigura()
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