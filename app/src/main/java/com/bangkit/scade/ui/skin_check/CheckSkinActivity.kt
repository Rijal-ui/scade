package com.bangkit.scade.ui.skin_check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityCheckSkinBinding

class CheckSkinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckSkinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckSkinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_check_1)
    }
}