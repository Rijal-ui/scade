package com.bangkit.scade.ui.detailinformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.scade.BuildConfig
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.databinding.ActivityDetailInformationBinding
import com.bumptech.glide.Glide

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
                Glide.with(this@DetailInformationActivity)
                    .load(BuildConfig.base_url_backend + "articles/image/" + data.thumbnail)
                    .into(imgInformation)

            }
        }
    }
}