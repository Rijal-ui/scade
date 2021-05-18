package com.bangkit.scade.di

import android.content.Context
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.remote.RemoteDataSource
import com.bangkit.scade.service.ApiConfig
import com.bangkit.scade.service.ApiInterface

object Injection {
    fun provideRepository(context: Context): Repository {
        val retrofit = ApiConfig.getInstance().create(ApiInterface::class.java)
        val remoteDataSource = RemoteDataSource(retrofit)
        return Repository.getInstance(remoteDataSource)
    }

}