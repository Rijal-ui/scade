package com.bangkit.scade.ui.hospital.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bangkit.scade.databinding.ActivityHospitalSplashBinding
import com.bangkit.scade.ui.hospital.HospitalActivity

class HospitalSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalSplashBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHospitalSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)
        handler.postDelayed(
            {
                binding.btnHospitalStart.visibility = View.VISIBLE
            }, 2000
        )

        binding.btnHospitalStart.setOnClickListener {
            val intent = Intent(this, HospitalActivity::class.java)
            startActivity(intent)
        }
    }
}