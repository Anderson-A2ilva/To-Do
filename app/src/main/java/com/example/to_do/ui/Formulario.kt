package com.example.to_do.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do.adapter.AdapterAtividadeList
import com.example.to_do.adapter.AdapterRecyclerView
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.Atividade
import com.example.to_do.data.model.AtividadesLista
import com.example.to_do.databinding.ActivityFormularioBinding
import com.example.to_do.databinding.UiDialogAdicionaAtividadeBinding
import kotlinx.coroutines.launch

class Formulario : AppCompatActivity() {
    private var atividadeList = mutableListOf<AtividadesLista>()
    private  lateinit var adapter: AdapterAtividadeList

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
        configuraBotaoSalvar()
        configuraReCyclerView()
        configuraAtividadeDialog()
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

    private fun configuraAtividadeDialog() {
        binding.textFieldFormularioAtividadeAdicionarAtividadeText.setOnClickListener {
            val atividadeDialog = UiDialogAdicionaAtividadeBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(this)
                .setView(atividadeDialog.root)
                .setPositiveButton("comfirmar") {_,_ ->
                    val nomeAtividade =
                        atividadeDialog.textFieldDialogAtividadeNome.editText?.text.toString()
                    val novaAtividade = AtividadesLista(nomeAtividade = nomeAtividade, cheked = false)
                    atividadeList.add(novaAtividade)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancelar") {_,_ ->

                }
                .create()
            dialog.show()
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

    private fun configuraReCyclerView(){
        adapter = AdapterAtividadeList(this,atividadeList) {afazer, isChecked ->
            afazer.cheked
        }
        binding.formularioAtividadeRecyclerView.adapter = adapter
        binding.formularioAtividadeRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun configuraBotaoSalvar(){
        val botaoSalvar = binding.formularioAtividadeBotaoSalvar

        botaoSalvar.setOnClickListener {
            val atividadeNova = criaFormularioAtividade()
            lifecycleScope.launch {
                formularioDao.salva(atividadeNova)
                Log.d("salvaDados", "configuraBotaoSalvar: ${criaFormularioAtividade()}}", )
                finish()
            }
        }
    }

    private fun criaFormularioAtividade(): Atividade {
        val nomeFormularioAtividade = binding.textFieldFormularioAtividadeNomeText.text.toString()
        val dataFormularioAtividade = binding.textFieldFormularioAtividadeDataText.text.toString()
        val prioridadeFormularioAtividade = binding.textFieldFormularioAtividadePrioridadeText.text.toString()

        val atividadeNova = Atividade(
            nome = nomeFormularioAtividade,
            data = dataFormularioAtividade,
            prioridade = prioridadeFormularioAtividade,
            atividadeList = atividadeList
        )
        return atividadeNova
    }

}
