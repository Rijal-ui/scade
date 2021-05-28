package com.bangkit.scade.ui.hospital.detail_hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.data.source.local.entity.GetDiagnosesEntity
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.databinding.ActivityDetailHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Resource
import com.bangkit.scade.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailHospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHospitalBinding
    private lateinit var viewModel: DetailHospitalViewModel
    private lateinit var detailHospital: Resource<HospitalEntity>
    private lateinit var detailDiagnose: Resource<GetDiagnosesEntity>
    private var diagnose: String = ""

    companion object {
        const val EXTRA_ID_HOSPITAL = "extra_id_hospital"
        const val EXTRA_ID_DIAGNOSE = "extra_id_diagnose"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailHospitalViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val idDiagnose = extras.getInt(EXTRA_ID_DIAGNOSE)
            val idHospital = extras.getInt(EXTRA_ID_HOSPITAL)

            viewModel.getDataDiagnose(idDiagnose)
            viewModel.getDataHospital(idHospital)

            viewModel.dataDiagnose.observe(this, { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        detailDiagnose = result
                        populateDataDiagnose(result)
                        diagnose = populateDataDiagnose(result).toString()
                        Log.d("diagnosecheck", diagnose)
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            this,
                            "Wrong Diagnose",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })

            viewModel.dataHospital.observe(this, { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        detailHospital = result
                        populateDataHospital(result)
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            this,
                            "Wrong Hospital",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }
            })
        }
    }

    private fun populateDataHospital(data: Resource<HospitalEntity>) {
        with(binding) {
            tvNameHospital.text = data.data?.name
            tvLocationHospital.text = data.data?.address
        }
    }

    private fun populateDataDiagnose(data: Resource<GetDiagnosesEntity>) {
        with(binding) {
            tvNameSpot.text = data.data?.position
            tvContentCancer.text = data.data?.cancerName

//            Glide.with(this@DetailHospitalActivity)
//                .load()
//                .apply(RequestOptions())
//                .into(imageCancer)
        }
    }
}