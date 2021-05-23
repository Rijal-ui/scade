package com.bangkit.scade.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityLoginBinding
import com.bangkit.scade.ui.home.HomeActivity
import com.bangkit.scade.ui.register.RegisterActivity
import com.bangkit.scade.ui.register.RegisterActivity.Companion.REQUEST_REGISTER

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            //check login

            //
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_REGISTER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == REQUEST_REGISTER && resultCode == RESULT_OK) {
            binding.editEmail.setText(data?.getStringExtra("email"))
        }
    }

}