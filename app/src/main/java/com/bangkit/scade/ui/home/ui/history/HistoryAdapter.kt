package com.bangkit.scade.ui.home.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.scade.data.source.local.entity.InvoicesEntity
import com.bangkit.scade.databinding.ItemListHistoryBinding
import com.bangkit.scade.ui.home.ui.history.detail_history.DetailHistoryActivity

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var listHistory = ArrayList<InvoicesEntity>()

    fun setHistory(history: List<InvoicesEntity>?) {
        if (history == null) return
        this.listHistory.clear()
        this.listHistory.addAll(history)
    }

    inner class ViewHolder(private val binding: ItemListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(history: InvoicesEntity) {
                with(binding) {
                    titleSpot.text = history.cancerPosition
                    resultSpot.text = history.cancerName

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailHistoryActivity::class.java).apply {
                            putExtra(DetailHistoryActivity.EXTRA_ID_HISTORY, history.invoiceId)
                        }
                        it.context.startActivity(intent)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListHistory = ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListHistory)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val history = listHistory[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = listHistory.size
}