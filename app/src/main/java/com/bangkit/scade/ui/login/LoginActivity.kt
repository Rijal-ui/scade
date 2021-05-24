package com.bangkit.scade.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityLoginBinding
import com.bangkit.scade.ui.home.HomeActivity
import com.bangkit.scade.ui.register.RegisterActivity
import com.bangkit.scade.ui.register.RegisterActivity.Companion.REQUEST_REGISTER
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.isEnabled = false
        binding.btLogin.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.darker_gray
            )
        )

        binding.btLogin.setOnClickListener {
            //check login

            //
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val emailStream = RxTextView.textChanges(binding.editEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.editPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe()

        val invalidStream = Observable.combineLatest(
            emailStream,

            passwordStream,
            { emailInvalid: Boolean,
              passwordInvalid: Boolean ->
                !emailInvalid &&
                        !passwordInvalid
            }
        )
        invalidStream.subscribe { isValid ->
            if (isValid) {
                binding.btLogin.isEnabled = true
                binding.btLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
            } else {
                binding.btLogin.isEnabled = false
                binding.btLogin.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.darker_gray
                    )
                )
            }
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

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.editEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

}