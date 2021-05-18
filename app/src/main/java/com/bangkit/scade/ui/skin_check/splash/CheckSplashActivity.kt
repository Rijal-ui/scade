package com.bangkit.scade.ui.skin_check.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.bangkit.scade.databinding.ActivityCheckSplashBinding
import com.bangkit.scade.ui.skin_check.CheckSkinActivity

class CheckSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckSplashBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)
        handler.postDelayed(
            {
                binding.btnCheckStart.visibility = View.VISIBLE
            }, 2000
        )

        binding.btnCheckStart.setOnClickListener {
            val intent = Intent(this, CheckSkinActivity::class.java)
            startActivity(intent)
        }
    }
}