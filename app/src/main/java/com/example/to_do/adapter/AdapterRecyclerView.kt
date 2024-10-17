package com.example.to_do.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.R
import com.example.to_do.data.model.Atividade
import com.example.to_do.databinding.UiCardHomeAtividadesBinding

class AdapterRecyclerView(
    private val context: Context,
    private val atividade: List<Atividade> = listOf(),
    var clickInformacoes: (atividade: Atividade) -> Unit = {},
    var quandoClicarEmEditar: (atividade: Atividade) -> Unit = {},
    var quandoClicarEmRemover: (atividade: Atividade) -> Unit = {}
) : RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>() {
    private var atividadeList = atividade.toMutableList()

    inner class ViewHolder(private val binding: UiCardHomeAtividadesBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {
        private lateinit var atividade: Atividade

        init {
            itemView.setOnClickListener {
                if (::atividade.isInitialized) {
                    clickInformacoes(atividade)
                }
            }

            binding.uiCardIconMore.setOnClickListener {
                if (::atividade.isInitialized) {
                    mostraPopupMenu(it)
                }
            }
        }

        fun vincula (atividade: Atividade) {
            this.atividade = atividade
            val nome = binding.uiCardAtividadeHomeNome
            nome.text = atividade.nome

            val data = binding.uiCardAtividadeHomeData
            data.text = atividade.data

            val prioridade = binding.uiCardAtividadeHomePrioridade
            prioridade.text = atividade.prioridade

            val cor = when (atividade.prioridade){
                "Alta" -> ContextCompat.getColor(context, R.color.Red)
                "Média" -> ContextCompat.getColor(context, R.color.Yellow)
                "Baixa" -> ContextCompat.getColor(context, R.color.Green)
                else -> ContextCompat.getColor(context, R.color.white)
            }

            val drawable = ContextCompat.getDrawable(context,R.drawable.icon_prioridade)
            drawable?.setTint(cor)
            binding.uiCardIconPrioridade.setImageDrawable(drawable)
        }

        private fun mostraPopupMenu(view: android.view.View) {
            val  popupMenu = PopupMenu(context, view)
            popupMenu.inflate(R.menu.pop_up_menu)
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId){
                    R.id.menu_detalhes_classe_editar -> {
                        quandoClicarEmEditar(atividade)
                        true
                    }
                    R.id.menu_detalhes_classe_deletar -> {
                        quandoClicarEmRemover(atividade)
                        true
                    } else -> false
                }
            }
                return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UiCardHomeAtividadesBinding.inflate(LayoutInflater.from(context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = atividadeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val atividade = atividadeList[position]
        holder.vincula(atividade)
    }

    fun atualiza(atividade: List<Atividade>){
        Log.d("AdapterRecyclerView", "Atualizando com: $atividade")
        atividadeList.clear()
        atividadeList.addAll(atividade)
        notifyDataSetChanged()
    }
}