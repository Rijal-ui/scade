package com.bangkit.scade.ui.skin_check

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityCheckSkinBinding
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CheckSkinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckSkinBinding
    private lateinit var currentPhotoPath: String
    private lateinit var file: File
    private lateinit var viewModel: CheckSkinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckSkinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[CheckSkinViewModel::class.java]

        viewModel.resultCheckSkin.observe(this, { result ->
            binding.progressBar.visibility = View.GONE
            binding.tvCheckResult.text = result.data[0]
            binding.tvCheckResult.visibility = View.VISIBLE
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.splash_check_1)

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(permissionListener).check()

        binding.imgCheck.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.btnCheckSkin.setOnClickListener {
            try {
                if (file.exists()) {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.checkSkinCancer(file)

                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.file_null),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: UninitializedPropertyAccessException) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.file_null),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            file = File(currentPhotoPath)
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath);
            Glide.with(this)
                .load(myBitmap)
                .into(binding.imgCheck)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.bangkit.scade.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private object permissionListener : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?
        ) {
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

    companion object {
        val CAMERA_REQUEST_CODE = 1
    }

}