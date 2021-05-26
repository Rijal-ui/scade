package com.bangkit.scade.ui.skin_check

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.databinding.ActivityCheckSkinBinding
import com.bangkit.scade.ui.hospital.HospitalActivity
import com.bangkit.scade.ui.hospital.HospitalActivity.Companion.EXTRA_ID_DIAGNOSE
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status.*
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CheckSkinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckSkinBinding
    private lateinit var currentPhotoPath: String
    private lateinit var file: File
    private lateinit var viewModel: CheckSkinViewModel
    private var idDiagnoses: Int? = null

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
            when (result.status) {
                SUCCESS -> {
                    viewModel.getSession().observe(this, { session ->
                        result.data?.let {
                            Log.d("inisessioncheck", session.tokenSection)
                            viewModel.createDiagnoses(
                                session.tokenSection,
                                result.data.data[0],
                                file,
                                binding.edtSpot.text.toString().trim()
                            )
                            viewModel.idDiagonse.observe(this, { diagnoses ->
                                when (diagnoses.status) {
                                    SUCCESS -> {
                                        idDiagnoses = diagnoses.data?.data!!
                                        result.data.let {
                                            binding.tvCheckResult.text = result.data.data[0]
                                            binding.progressBar.visibility = View.GONE
                                            binding.tvCheckResult.visibility = View.VISIBLE
                                            binding.btnBooking.visibility = View.VISIBLE
                                        }
                                    }
                                    LOADING -> {
                                        binding.progressBar.visibility = View.VISIBLE
                                    }
                                    ERROR -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                    })
//                    viewModel.session.observe(this, { session ->
//                        result.data?.let {
//                            viewModel.createDiagnoses(
//                                session,
//                                result.data.data[0],
//                                file,
//                                binding.edtSpot.text.toString().trim()
//                            )
//                            viewModel.idDiagonse.observe(this, { diagnoses ->
//                                when (diagnoses.status) {
//                                    SUCCESS -> {
//                                        idDiagnoses = diagnoses.data?.data!!
//                                        result.data.let {
//                                            binding.tvCheckResult.text = result.data.data[0]
//                                            binding.progressBar.visibility = View.GONE
//                                            binding.tvCheckResult.visibility = View.VISIBLE
//                                            binding.btnBooking.visibility = View.VISIBLE
//                                        }
//                                    }
//                                    LOADING -> {
//                                        binding.progressBar.visibility = View.VISIBLE
//                                    }
//                                    ERROR -> {
//                                    }
//                                }
//                            })
//                        }
//                    })

                }
                LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT)
                        .show()
                }
            }
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

        binding.btnBooking.setOnClickListener {
            //ngirim data variabel yg disimpan tadi lewat intent
            val intent = Intent(this, HospitalActivity::class.java)
            intent.putExtra(EXTRA_ID_DIAGNOSE, idDiagnoses)
            startActivity(intent)
        }

        binding.btnCheckSkin.setOnClickListener {
            //check apakah edit text nya udah diisi atau belum
            //jika sudah jalankan fungsi on click
            //jika belum kasih toast harap diisi
            if (!binding.edtSpot.text.isEmpty()) {
                try {
                    if (file.exists()) {
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
            } else {
                binding.edtSpot.error = getString(R.string.edit_text_empy_error)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            file = File(currentPhotoPath)
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
            var cropbitmap: Bitmap? = null
            if (myBitmap.width >= myBitmap.height) {
                cropbitmap = Bitmap.createBitmap(
                    myBitmap,
                    myBitmap.width / 2 - myBitmap.height / 2,
                    0,
                    myBitmap.height,
                    myBitmap.height
                )
            } else {
                cropbitmap = Bitmap.createBitmap(
                    myBitmap,
                    0,
                    myBitmap.height / 2 - myBitmap.width / 2,
                    myBitmap.width,
                    myBitmap.width
                )
            }


            val os = ByteArrayOutputStream()
            cropbitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            val bitmaparray = os.toByteArray()

            val fos = FileOutputStream(file)
            fos.write(bitmaparray)
            fos.flush()
            fos.close()

            val checkbitmapcroped = BitmapFactory.decodeFile(file.absolutePath)

            Glide.with(this)
                .load(checkbitmapcroped)
                .into(binding.imgCheck)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(
            MediaStore.ACTION_IMAGE_CAPTURE
        ).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.bangkit.scade.fileprovider",
                        it
                    )
                    intent.putExtra("aspectX", 1)
                    intent.putExtra("aspectY", 1)
                    intent.putExtra("scale", true)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, CAMERA_REQUEST_CODE)
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