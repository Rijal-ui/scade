package com.bangkit.scade.ui.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils
import com.bangkit.scade.R

class HospitalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_hospital_1)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

}