package com.bangkit.scade.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityLoginBinding
import com.bangkit.scade.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            //check login

            //
            startActivity(intent)
        }

    }
}