package com.bangkit.scade.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.bangkit.scade.R
import com.bangkit.scade.ui.home.HomeActivity

class MainSplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    private var loginToken = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash)

        if (loginToken == true) {
            handler = Handler(mainLooper)
            handler.postDelayed(
                {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2500
            )
        } else if (loginToken == false) {
         Toast.makeText(this, getString(R.string.token_expired), Toast.LENGTH_SHORT).show()
        } else {
            // Back to Login
            Toast.makeText(this, getString(R.string.no_token), Toast.LENGTH_SHORT).show()
        }
    }
}