package com.bangkit.scade.ui.hospital

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityHospitalBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status

class HospitalActivity : AppCompatActivity() {

    private lateinit var viewModel: HospitalViewModel
    private lateinit var adapter: HospitalAdapter
    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
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

        binding.findHospital.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                    result.data?.let { adapter.setHospital(result.data) }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    companion object {
        const val EXTRA_ID_DIAGNOSE = "extra_id_diagnose"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}