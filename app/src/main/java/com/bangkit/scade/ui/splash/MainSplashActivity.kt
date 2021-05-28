package com.bangkit.scade.ui.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.ui.home.HomeActivity
import com.bangkit.scade.ui.login.LoginActivity
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status
import com.bangkit.scade.vo.Status.LOADING
import com.bangkit.scade.vo.Status.SUCCESS

class MainSplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    private lateinit var viewModel: MainSplashViewModel
    private var exist: Boolean = false
    private lateinit var token: String
    private var session: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[MainSplashViewModel::class.java]

        viewModel.checkExist().observe(this, {
            exist = it
//            Log.d("inicheckexist", exist.toString())
            if (exist) {
                viewModel.setSession().observe(this, { entity ->
                    token = entity.tokenSession
//                    Log.d("inichecktokenac", entity.tokenSession)
                    viewModel.checkSession(token)
//                    viewModel.session.observe(this, { result ->
//                        when (result.status) {
//                            SUCCESS -> {
////                                session = result.message.toString()
//                                session = result
//                                Log.d("inisession", session)
//                            }
//                            LOADING -> {
//                            }
//                            Status.ERROR -> {
//                                Toast.makeText(
//                                    this,
//                                    getString(R.string.error_message),
//                                    Toast.LENGTH_SHORT
//                                )
//                                    .show()
//                            }
//                        }
//                    })
                })
            }

        })





        handler = Handler(mainLooper)
        handler.postDelayed(
            {

                if (exist) { //sudah pernah login
                    //check session expired atau tidak
                    //ambil token dari database
                    if (isNetworkAvailable()) {
                        viewModel.session.observe(this, { result ->
                            when (result.status) {
                                SUCCESS -> {
                                    if (result.data?.message != null) {
                                        session = result.data.message
//                                        Log.d("inisession", session)
                                    }
                                }
                                LOADING -> {
                                }
                                Status.ERROR -> {
                                    if (!isNetworkAvailable()) {
                                        val intent = Intent(this, HomeActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.error_message),
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                            }

                        })
                        if (session == "fetch data successfully") { //session masih oke
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else { //session expired
                            Toast.makeText(
                                this,
                                getString(R.string.session_end),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                } else { //belum pernah login
                    Toast.makeText(this, "No login history", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 2000
        )


    }

    private fun isNetworkAvailable(): Boolean {
        val connectManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = connectManager.activeNetworkInfo
        return internetInfo != null && internetInfo.isConnected
    }
}