package com.bangkit.scade.ui.hospital.detail_hospital

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.data.source.local.entity.GetDiagnosesEntity
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.databinding.ActivityDetailHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Resource
import com.bangkit.scade.vo.Status.*

class DetailHospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHospitalBinding
    private lateinit var viewModel: DetailHospitalViewModel
    private lateinit var detailHospital: Resource<HospitalEntity>
    private lateinit var detailDiagnose: Resource<GetDiagnosesEntity>
    private var token: String = ""
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

        viewModel.getSession().observe(this, { tokenSession ->
            token = tokenSession.tokenSession
            val extras = intent.extras
            if (extras != null) {
                val idDiagnose = extras.getInt(EXTRA_ID_DIAGNOSE)
                Log.d("iniiddiagnose", idDiagnose.toString())
//            val idHospital = extras.getInt(EXTRA_ID_HOSPITAL)

                viewModel.getDataDiagnose(token, idDiagnose)
                Log.d("token", token)
                Log.d("idDiagnose", idDiagnose.toString())
//            viewModel.getDataHospital(idHospital)
//            Log.d("idhospital", idHospital.toString())

                viewModel.dataDiagnose.observe(this, { result ->
                    when (result.status) {
                        SUCCESS -> {
                            populateDataDiagnose(result)
                            result.data?.let {
                                Log.d("diagnosecheck", result.data.cancerName.toString())
                            }
                        }
                        LOADING -> {
                        }
                        ERROR -> {
                            Toast.makeText(
                                this,
                                "Wrong Diagnose",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                })

//            viewModel.dataHospital.observe(this, { result ->
//                when(result.status) {
//                    SUCCESS -> {
//                        binding.tvNameHospital.text = result.data?.name
//                        binding.tvLocationHospital.text = result.data?.province
////                        detailHospital = result
////                        populateDataHospital(result)
//                    }
//                    LOADING -> {
//                    }
//                    ERROR -> {
//                        Toast.makeText(
//                            this,
//                            "Wrong Hospital",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }
//
//                }
//            })
            }
        })
        Log.d("thisToken", token)


    }

    private fun populateDataHospital(data: Resource<HospitalEntity>) {
        with(binding) {
            tvNameHospital.text = data.data?.name
            tvLocationHospital.text = data.data?.province
        }
    }

    private fun populateDataDiagnose(data: Resource<GetDiagnosesEntity>) {
        with(binding) {
            tvNameSpot.text = data.data?.position
            Log.d("tvNameSpot", tvNameSpot.text.toString())
            tvContentCancer.text = data.data?.cancerName
//            tvNameSpot.text = data.position
//            tvContentCancer.text = data.cancerName

//            Glide.with(this@DetailHospitalActivity)
//                .load()
//                .apply(RequestOptions())
//                .into(imageCancer)
        }
    }
}