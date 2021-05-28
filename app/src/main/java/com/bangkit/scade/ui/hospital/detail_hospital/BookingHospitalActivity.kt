package com.bangkit.scade.ui.hospital.detail_hospital

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.data.source.local.entity.GetDiagnosesEntity
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.data.source.remote.response.InvoiceRequest
import com.bangkit.scade.databinding.ActivityBookingHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Resource
import com.bangkit.scade.vo.Status.*

class BookingHospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingHospitalBinding
    private lateinit var viewModel: BookingHospitalViewModel
    private lateinit var detailHospital: Resource<HospitalEntity>
    private lateinit var detailDiagnose: Resource<GetDiagnosesEntity>
    private var idDiagnose: Int = 1
    private var idHospital: Int = 1
    private var token: String = ""

    companion object {
        const val EXTRA_ID_HOSPITAL = "extra_id_hospital"
        const val EXTRA_ID_DIAGNOSE = "extra_id_diagnose"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.booking)

        binding = ActivityBookingHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[BookingHospitalViewModel::class.java]

        viewModel.getSession().observe(this, { tokenSession ->
            token = tokenSession.tokenSession
            val extras = intent.extras
            if (extras != null) {
                idDiagnose = extras.getInt(EXTRA_ID_DIAGNOSE)
                idHospital = extras.getInt(EXTRA_ID_HOSPITAL)

                binding.btnCreateBooking.setOnClickListener {
                    val invoiceData = InvoiceRequest(
                        hospital_id = idHospital,
                        diagnose_id = idDiagnose
                    )

                    viewModel.createInvoice(token, invoiceData)

                    Log.d("dignose_id", invoiceData.diagnose_id.toString())


                }

                viewModel.getDataDiagnose(token, idDiagnose)
                viewModel.getDataHospital(idHospital)

                viewModel.dataDiagnose.observe(this, { result ->
                    when (result.status) {
                        SUCCESS -> {
                            populateDataDiagnose(result)
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

                viewModel.dataHospital.observe(this, { result ->
                    when (result.status) {
                        SUCCESS -> {
                            binding.tvNameHospital.text = result.data?.name
                            binding.tvLocationHospital.text = result.data?.province
//                        detailHospital = result
//                        populateDataHospital(result)
                        }
                        LOADING -> {
                        }
                        ERROR -> {
                            Toast.makeText(
                                this,
                                "Wrong Hospital",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }
                })
                viewModel.invoice.observe(this, { result ->
                    when (result.status) {
                        SUCCESS -> {
                            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                        }
                        LOADING -> {

                        }
                        ERROR -> {
                            Toast.makeText(this, "fail to book", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
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