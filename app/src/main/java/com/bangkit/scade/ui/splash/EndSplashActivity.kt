package com.bangkit.scade.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.scade.databinding.ActivityEndSplashBinding
import com.bangkit.scade.ui.home.HomeActivity

class EndSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEndSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEndSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar?.hide()
        }

        binding.btnToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}