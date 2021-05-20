package com.bangkit.scade.di

import android.content.Context
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.LocalDataSource
import com.bangkit.scade.data.source.local.room.DataDatabase
import com.bangkit.scade.data.source.remote.RemoteDataSource
import com.bangkit.scade.service.ApiConfig
import com.bangkit.scade.service.ApiInterface
import com.bangkit.scade.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = DataDatabase.getInstance(context)
        val retrofit = ApiConfig.getInstance().create(ApiInterface::class.java)
        val remoteDataSource = RemoteDataSource(retrofit)
        val localDataSource = LocalDataSource.getInstance(database.dataDao())
        val appExecutor = AppExecutors()
        return Repository.getInstance(remoteDataSource, localDataSource, appExecutor)
    }

}