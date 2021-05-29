package com.bangkit.scade.ui.home.ui.history.detail_history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.data.source.local.entity.InvoicesEntity
import com.bangkit.scade.databinding.ActivityDetailHistoryBinding
import com.bangkit.scade.ui.hospital.detail_hospital.BookingHospitalViewModel
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Resource
import com.bangkit.scade.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var viewModel: DetailHistoryViewModel
    private lateinit var detailHistory: Resource<InvoicesEntity>
    private var idHistory: Int = 1
    private var token: String = ""

    companion object {
        const val EXTRA_ID_HISTORY = "extra_id_history"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailHistoryViewModel::class.java]

        viewModel.getSession().observe(this, { tokenSession ->
            token = tokenSession.tokenSession
            val extras = intent.extras
            if (extras != null) {
                idHistory = extras.getInt(EXTRA_ID_HISTORY)

                viewModel.getDataHistory(token, idHistory)

                viewModel.dataHistory.observe(this, { result ->
                    when(result.status) {
                        Status.SUCCESS -> {
                            detailHistory = result
                            populateDataHistory(result)
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                        }
                    }
                })
            }
        })
    }

    private fun populateDataHistory(data: Resource<InvoicesEntity>) {
        with(binding) {
            tvContentCancer.text = (getString(R.string.name_cancer) + " : ${data.data?.cancerName}")
            tvNameSpot.text = (getString(R.string.name_spot) + " : ${data.data?.cancerPosition}")
            tvNameHospital.text = (getString(R.string.name_hospital) + " : ${data.data?.hospitalName}")
            tvPhoneHospital.text = (getString(R.string.phone) + " : ${data.data?.hospitalPhone}")
            tvLocationHospital.text = (getString(R.string.location) +
                    " : ${data.data?.hospitalAddress}, ${data.data?.hospitalCity}, ${data.data?.hospitalProvince}")

            Glide.with(this@DetailHistoryActivity)
                .load("http://35.213.130.133:8080/diagnoses/image/" + data.data?.cancerImage)
                .apply(RequestOptions())
                .into(imageCancer)
        }
    }
}