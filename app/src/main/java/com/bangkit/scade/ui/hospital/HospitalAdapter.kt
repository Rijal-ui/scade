package com.bangkit.scade.ui.hospital

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.databinding.ItemListHospitalBinding
import com.bangkit.scade.ui.hospital.detail_hospital.BookingHospitalActivity

class HospitalAdapter : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    private var listHospital = ArrayList<HospitalEntity>()
    var idDiagnose: Int? = null

    fun setHospital(hospital: List<HospitalEntity>?) {
        if (hospital == null) return
        this.listHospital.clear()
        this.listHospital.addAll(hospital)
    }

    inner class ViewHolder (private val binding: ItemListHospitalBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(hospital: HospitalEntity) {
                with(binding) {
                    tvHospitalTitle.text = hospital.name
                    tvHospitalLocation.text = ("${hospital.address}, ${hospital.city}, ${hospital.province}")

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, BookingHospitalActivity::class.java).apply {
                            putExtra(BookingHospitalActivity.EXTRA_ID_HOSPITAL, hospital.id)
                            putExtra(BookingHospitalActivity.EXTRA_ID_DIAGNOSE, idDiagnose)
                        }
                        it.context.startActivity(intent)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListHospital = ItemListHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListHospital)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hospital = listHospital[position]
        holder.bind(hospital)
    }

    override fun getItemCount(): Int = listHospital.size

    fun setIdDiagnose(id: Int) {
        idDiagnose = id
    }
}