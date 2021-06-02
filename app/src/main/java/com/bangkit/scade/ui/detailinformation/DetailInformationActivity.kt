package com.bangkit.scade.ui.detailinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.scade.R
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.databinding.ActivityDetailInformationBinding

class DetailInformationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<InformationEntity>(EXTRA_DATA)
        showInformation(data)
    }

    private fun showInformation(data: InformationEntity?) {
        data?.let {
            with(binding) {
                tvTitle.text = data.title
                tvContent.text = data.body
                //here is the image

            }
        }
    }
}