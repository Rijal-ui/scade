package com.bangkit.scade.ui.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status

class HospitalActivity : AppCompatActivity() {

    //JANGAN LUPA DIBALIKAN SEARCHVIEW NYA

    private lateinit var viewModel: HospitalViewModel
    private lateinit var adapter: HospitalAdapter
    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ALURNYA GINI mungkin agak muter muter soalnya nge klik nya lewat item list nya jadi harus nyambungin intent extra di activity ke adapter
        //search view nya jika diisi mulai nembak API hospital/search nya
        //nanti tampilin bentuk list
        //ketika di klik satu maka akan masuk ke halaman buat rujukan dengan intent yg ngirim id hospital sama data yg didapat dari intent extra activity sebelumnya


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_hospital_1)

        adapter = HospitalAdapter()
        adapter.notifyDataSetChanged()

        binding.rvListHospital.layoutManager = LinearLayoutManager(this)
        binding.rvListHospital.adapter = adapter

        val extras = intent.getIntExtra(EXTRA_ID_DIAGNOSE, 1)
        adapter.setIdDiagnose(extras)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[HospitalViewModel::class.java]

        binding.findHospital.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank() || query.length < 3) return false
                viewModel.setSearchHospital(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank()) return false
                viewModel.setSearchHospital(query)
                return false
            }
        })

        viewModel.setListHospital()

        viewModel.listHospital.observe(this, { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { adapter.setHospital(result.data)}
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        val EXTRA_ID_DIAGNOSE = "extra_id_diagnose"
    }

}