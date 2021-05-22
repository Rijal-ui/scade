package com.bangkit.scade.ui.hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityHospitalBinding
import com.bangkit.scade.ui.booking_hospital.BookingHospitalActivity

class HospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_hospital_1)

        binding.findHospital.setOnClickListener {
            val intent = Intent(this, BookingHospitalActivity::class.java)
            startActivity(intent)
        }
    }
}