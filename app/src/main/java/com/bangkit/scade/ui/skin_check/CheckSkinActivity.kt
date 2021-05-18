package com.bangkit.scade.ui.skin_check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R

class CheckSkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_skin)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_check_1)
    }
}