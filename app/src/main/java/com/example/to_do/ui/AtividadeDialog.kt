package com.example.to_do.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.to_do.R
import com.example.to_do.data.dataBase.AppDataBase
import com.example.to_do.data.model.Atividade
import kotlinx.coroutines.launch

class AtividadeDialog : DialogFragment() {

    private val formularioDao by lazy {
        val db = AppDataBase.instancia(requireContext())
        db.formularioDao()
    }

    private lateinit var atividade: Atividade

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        atividade = requireArguments().getParcelable<Atividade>("atividade") ?: return super.onCreateDialog(savedInstanceState)

        return AlertDialog.Builder(requireActivity())
            .setView(createView())
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("Concluida") {dialog, _ ->
                marcaComoConcluida()
                dialog.dismiss()
            }
            .create()
    }


    private fun createView(): View {
        val view = layoutInflater.inflate(R.layout.ui_dialog_informacao_task, null)

        view.findViewById<TextView>(R.id.dialog_task_infomacao_nome).text = atividade.nome
        view.findViewById<TextView>(R.id.dialog_task_infomacao_data).text = "Data:${atividade.data}"
        view.findViewById<TextView>(R.id.dialog_task_infomacao_hora).text = "Hora: ${atividade.hora}"
        view.findViewById<TextView>(R.id.dialog_task_infomacao_prioridade).text = "Prioridade: ${atividade.prioridade}"
        view.findViewById<TextView>(R.id.dialog_task_infomacao_categoria).text = "Categoria: ${atividade.categoria}"
        view.findViewById<TextView>(R.id.dialog_task_infomacao_text_task).text = atividade.atividadeText

        return view
    }

    private fun marcaComoConcluida() {
        atividade.isConcluida = !atividade.isConcluida


        lifecycleScope.launch {
            val db = formularioDao
            db.atualiza(atividade)

        }
    }

    companion object {
        fun newInstance(atividade: Atividade): AtividadeDialog {
            val fragment = AtividadeDialog()
            val args = Bundle()
            args.putParcelable("atividade",atividade)
            fragment.arguments = args
            return fragment
        }
    }
}