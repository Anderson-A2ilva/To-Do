package com.example.to_do.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.Atividade
import com.example.to_do.data.model.AtividadesLista
import com.example.to_do.databinding.ActivityFormularioBinding
import kotlinx.coroutines.launch

class Formulario : AppCompatActivity() {
    private var idAtividade = 0L

    private val formularioDao by lazy {
        val db = AppDataBase.instancia(this)
        db.formularioDao()
    }

    private val binding by lazy {
        ActivityFormularioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraDataPicker()
        configuraSpinnerPrioridade()
    }

    private fun configuraDataPicker() {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val dataEditText = binding.textFieldFormularioAtividadeDataText

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val formattedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                dataEditText?.setText(formattedDate)
            },
            ano,
            mes,
            dia
        )
        dataEditText.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun configuraSpinnerPrioridade() {
        val prioridades = arrayOf("Prioridade", "Baixa", "Média", "Alta")
        val autoCompletePrioridade =
            binding.textFieldFormularioAtividadePrioridade.editText as? AutoCompleteTextView
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            prioridades
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompletePrioridade?.setAdapter(adapter)
        autoCompletePrioridade?.setOnClickListener {
            autoCompletePrioridade.showDropDown()
        }
    }

//    private fun configuraReCyclerView(){
//        adapter =
//    }

    private fun criaFormularioAtividade(): Atividade {
        val nomeFormularioAtividade = binding.textFieldFormularioAtividadeNomeText.text.toString()
        val dataFormularioAtividade = binding.textFieldFormularioAtividadeDataText.text.toString()
        val prioridadeFormularioAtividade =
            binding.textFieldFormularioAtividadePrioridadeText.toString()

        val atividadeNova = Atividade(
            nome = nomeFormularioAtividade,
            data = dataFormularioAtividade,
            prioridade = prioridadeFormularioAtividade,
            atividadeList = emptyList()
        )
        return atividadeNova
    }

}
