package com.bangkit.scade.ui.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityRegisterBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    companion object {
        val REQUEST_REGISTER = 1
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.isEnabled = false
        binding.btRegister.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.darker_gray
            )
        )
        val emailStream = RxTextView.textChanges(binding.editEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.editPasswordConfirmation)
                .map { password ->
                    password.toString() != binding.editPasswordConfirmation.text.toString() &&
                            password.isEmpty()
                },
            RxTextView.textChanges(binding.editPasswordConfirmation)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.editPassword.text.toString() ||
                            confirmPassword.isEmpty()
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

        val passwordStream = RxTextView.textChanges(binding.editPassword)
            .map { charSec ->
                charSec.isEmpty()
            }
        passwordStream.subscribe()

        val invalidStream = Observable.combineLatest(
            emailStream,
            passwordConfirmationStream,
            nameStream,
            addressStream,
            passwordStream,
            { emailInvalid: Boolean,
              passwordConfirmationInvalid: Boolean,
              nameInvalid: Boolean,
              addressInvalid: Boolean,
              passwordInvalid: Boolean ->
                !emailInvalid &&
                        !passwordConfirmationInvalid &&
                        !nameInvalid &&
                        !addressInvalid &&
                        !passwordInvalid
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




        binding.btRegister.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("email", binding.editEmail.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.editEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }


    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.editPasswordConfirmation.error =
            if (isNotValid) getString(R.string.password_not_same) else null
    }
}