package com.example.to_do.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.Atividade
import com.example.to_do.databinding.ActivityFormularioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Formulario : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)
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
        configuraBotaoSalvar()
        configuraSpinnerCategoria()
        configuraTimePicker()
        editarAtividade()
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

    private fun configuraTimePicker() {
        val calendario = Calendar.getInstance()
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)
        val horaEditText = binding.textFieldFormularioAtividadeHoraText

        val timePickerDialog = TimePickerDialog(
            this,
            { _, horaSelecionada, minutoselecionado ->
                // Formata a string com a hora selecionada
                val formattedTime = String.format("%02d:%02d", horaSelecionada, minutoselecionado)
                horaEditText.setText(formattedTime)
            },
            hora,
            minuto,
            true // true para 24 horas, false para formato 12 horas
        )

        horaEditText.setOnClickListener {
            timePickerDialog.show()
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

    private fun configuraSpinnerCategoria() {
        val categoria = arrayOf("Categoria", "Pessoal", "Trabalho", "Casa")
        val autoCompletePrioridade =
            binding.textFieldFormularioAtividadeCategoria.editText as? AutoCompleteTextView
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categoria
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompletePrioridade?.setAdapter(adapter)
        autoCompletePrioridade?.setOnClickListener {
            autoCompletePrioridade.showDropDown()
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.formularioAtividadeBotaoSalvar

        botaoSalvar.setOnClickListener {
            val atividadeNova = criaFormularioAtividade()
            lifecycleScope.launch {
                if (idAtividade == 0L) {
                    formularioDao.salva(atividadeNova)
                    Log.d("salvaDados", "configuraBotaoSalvar: ${criaFormularioAtividade()}}")
                } else  {
                    atividadeNova.id = idAtividade
                    formularioDao.atualiza(atividadeNova)
                }
                finish()
            }
        }
    }

    private fun editarAtividade() {
        scope.launch {
            idAtividade = intent.getLongExtra(CHAVE_ATIVIDADE_ID, 0L)
            if (idAtividade != 0L) {
                formularioDao.buscaPorId(idAtividade)?.collect { atividade ->
                    withContext(Dispatchers.Main) {
                        atividade?.let { preencheFormularioComDadosAtividade(it) }
                    }
                }
            }
        }
    }

    private fun preencheFormularioComDadosAtividade(atividade: Atividade) {
        with(binding) {
            textFieldFormularioAtividadeNomeText.setText(atividade.nome)
            textFieldFormularioAtividadeDataText.setText(atividade.data)
            textFieldFormularioAtividadeHoraText.setText(atividade.hora)
            textFieldFormularioAtividadePrioridadeText.setText(atividade.prioridade, false)
            textFieldFormularioAtividadeCategoriaText.setText(atividade.categoria, false)
            editTextFormularioAtividadeTextText.setText(atividade.atividadeText)
        }
    }

    private fun criaFormularioAtividade(): Atividade {
        val nomeFormularioAtividade = binding.textFieldFormularioAtividadeNomeText.text.toString()
        val dataFormularioAtividade = binding.textFieldFormularioAtividadeDataText.text.toString()
        val horaFormularioAtividade = binding.textFieldFormularioAtividadeHoraText.text.toString()
        val prioridadeFormularioAtividade =
            binding.textFieldFormularioAtividadePrioridadeText.text.toString()
        val categoriaFormularioAtividade =
            binding.textFieldFormularioAtividadeCategoriaText.text.toString()
        val textFormularioAtividade = binding.editTextFormularioAtividadeTextText.text.toString()

        val atividadeNova = Atividade(
            nome = nomeFormularioAtividade,
            data = dataFormularioAtividade,
            hora = horaFormularioAtividade,
            prioridade = prioridadeFormularioAtividade,
            categoria = categoriaFormularioAtividade,
            atividadeText = textFormularioAtividade
        )
        return atividadeNova
    }

}
