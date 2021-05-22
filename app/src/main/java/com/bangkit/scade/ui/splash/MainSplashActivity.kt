package com.bangkit.scade.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkit.scade.R
import com.bangkit.scade.ui.home.HomeActivity
import com.bangkit.scade.ui.login.LoginActivity

class MainSplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash)

        handler = Handler(mainLooper)
        handler.postDelayed(
            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2500
        )

    }
}