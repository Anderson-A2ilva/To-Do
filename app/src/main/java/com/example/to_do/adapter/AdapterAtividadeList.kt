package com.example.to_do.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.data.model.AtividadesLista
import com.example.to_do.databinding.UiCardAtividadeListBinding

class AdapterAtividadeList(
    private val context: Context,
    private val afazeres : List<AtividadesLista>,
    private val chekedbox: (AtividadesLista,Boolean) -> Unit
) : RecyclerView.Adapter<AdapterAtividadeList.ViewHolder>() {

    inner class ViewHolder(private val binding: UiCardAtividadeListBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun vinculaAtividades (afazeres: AtividadesLista){
                    binding.checkedNomeAtividade.text = afazeres.nomeAtividade
                    binding.checkedBoxAtividade.isChecked = afazeres.cheked
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UiCardAtividadeListBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = afazeres.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val afazeres = afazeres[position]
        holder.vinculaAtividades(afazeres)
    }
}