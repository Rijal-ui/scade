package com.bangkit.scade.ui.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.data.source.remote.response.RegisterRequest
import com.bangkit.scade.databinding.ActivityRegisterBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status.*
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    companion object {
        val REQUEST_REGISTER = 1
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[RegisterViewModel::class.java]

        binding.btRegister.isEnabled = false
        binding.btRegister.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.darker_gray
            )
        )

        binding.btRegister.setOnClickListener {
            val registerData = RegisterRequest(
                address = binding.editAddress.text.toString(),
                name = binding.editName.text.toString(),
                email = binding.editEmail.text.toString(),
                password = binding.editPassword.text.toString(),
                phone = binding.editPhone.text.toString()
            )

            viewModel.register(registerData)

            viewModel.register.observe(this, { result ->
                when (result.status) {
                    SUCCESS -> {
                        val resultIntent = Intent()
                        resultIntent.putExtra("email", binding.editEmail.text.toString())
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                    LOADING -> {
                    }
                    ERROR -> {
                        Toast.makeText(
                            this,
                            getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }

        val emailStream = RxTextView.textChanges(binding.editEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.editPassword)
                .map { password ->
                    password.toString() != binding.editPasswordConfirmation.text.toString()
                },
            RxTextView.textChanges(binding.editPasswordConfirmation)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.editPassword.text.toString()
                }
        )

        passwordConfirmationStream.subscribe {
            showPasswordConfirmationAlert(it)
        }

        val nameStream = RxTextView.textChanges(binding.editName)
            .map { charSec ->
                charSec.isEmpty()
            }
        nameStream.subscribe()

        val addressStream = RxTextView.textChanges(binding.editAddress)
            .map { charSec ->
                charSec.isEmpty()
            }
        addressStream.subscribe()

        val phoneStream = RxTextView.textChanges(binding.editPhone)
            .skipInitialValue()
            .map { charSec ->
                !Patterns.PHONE.matcher(charSec).matches()
            }
        phoneStream.subscribe {
            showPhoneNumberExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.editPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

        val invalidStream = Observable.combineLatest(
            emailStream,
            passwordConfirmationStream,
            nameStream,
            addressStream,
            passwordStream,
            phoneStream,
            { emailInvalid: Boolean,
              passwordConfirmationInvalid: Boolean,
              nameInvalid: Boolean,
              addressInvalid: Boolean,
              passwordInvalid: Boolean,
              phoneInvalid: Boolean ->
                !emailInvalid &&
                        !passwordConfirmationInvalid &&
                        !nameInvalid &&
                        !addressInvalid &&
                        !passwordInvalid &&
                        !phoneInvalid
            }
        )
        invalidStream.subscribe { isValid ->
            if (isValid) {
                binding.btRegister.isEnabled = true
                binding.btRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
            } else {
                binding.btRegister.isEnabled = false
                binding.btRegister.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.darker_gray
                    )
                )
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.editEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPhoneNumberExistAlert(isNotValid: Boolean) {
        binding.editPhone.error =
            if (isNotValid) getString(R.string.phone_number_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.editPassword.error =
            if (isNotValid) getString(R.string.password_not_valid) else null
    }


    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.editPasswordConfirmation.error =
            if (isNotValid) getString(R.string.password_not_same) else null
    }
}