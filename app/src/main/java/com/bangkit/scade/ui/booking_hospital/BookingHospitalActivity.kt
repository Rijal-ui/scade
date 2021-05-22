package com.bangkit.scade.ui.booking_hospital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityBookingHospitalBinding
import com.bangkit.scade.ui.splash.EndSplashActivity

class BookingHospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.booking_a_hospital)

        binding.btnSend.setOnClickListener {
            val intent = Intent(this, EndSplashActivity::class.java)
            startActivity(intent)
        }
    }
}