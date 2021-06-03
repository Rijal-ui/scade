package com.bangkit.scade.service.retrofit

import com.bangkit.scade.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MLApiConfig {
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null
        fun getInstance(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            return retrofit ?: synchronized(this) {
                retrofit ?: Retrofit.Builder()
                    .baseUrl(BuildConfig.base_url_ML)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
        }
    }
}