package com.bangkit.scade.ui.home.ui.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.databinding.ItemListInformationBinding

class InformationAdapter : RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    private var listInformation = ArrayList<InformationEntity>()
    var onItemClick : ((InformationEntity) -> Unit)? = null

    fun setInformation(information: List<InformationEntity>?) {
        if (information == null) return
        this.listInformation.clear()
        this.listInformation.addAll(information)
    }

    inner class ViewHolder(private val binding: ItemListInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(information: InformationEntity) {
            with(binding) {
                tvInformationTitle.text = information.title
                tvContent.text = information.body
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listInformation[absoluteAdapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListInformation =
            ItemListInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListInformation)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val information = listInformation[position]
        holder.bind(information)
    }

    override fun getItemCount(): Int = listInformation.size
}