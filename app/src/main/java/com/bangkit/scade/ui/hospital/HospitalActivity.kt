package com.bangkit.scade.ui.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory

class HospitalActivity : AppCompatActivity() {

    private lateinit var viewModel: HospitalViewModel
    private lateinit var adapter: HospitalAdapter
    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_hospital_1)

        adapter = HospitalAdapter()
        adapter.notifyDataSetChanged()

        binding.rvListHospital.layoutManager = LinearLayoutManager(this)
        binding.rvListHospital.adapter = adapter

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[HospitalViewModel::class.java]

        viewModel.listHospital.observe(this, { list ->
            adapter.setHospital(list)
            adapter.notifyDataSetChanged()
        })
    }
}