package com.bangkit.scade.di

import android.content.Context
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.LocalDataSource
import com.bangkit.scade.data.source.local.room.DataDatabase
import com.bangkit.scade.data.source.remote.RemoteDataSource
import com.bangkit.scade.service.retrofit.ApiConfig
import com.bangkit.scade.service.ApiMLInterface
import com.bangkit.scade.service.retrofit.ArticleApiConfig
import com.bangkit.scade.service.ApiBackendInterface
import com.bangkit.scade.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = DataDatabase.getInstance(context)
        val retrofitML = ApiConfig.getInstance().create(ApiMLInterface::class.java)
        val retrofitArticle = ArticleApiConfig.getInstance().create(ApiBackendInterface::class.java)
        val remoteDataSourceML = RemoteDataSource(retrofitML, retrofitArticle)
        val localDataSource = LocalDataSource.getInstance(database.dataDao())
        val appExecutor = AppExecutors()
        return Repository.getInstance(remoteDataSourceML, localDataSource, appExecutor)
    }

}