package com.bangkit.scade.data.source.remote

import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import com.bangkit.scade.service.ApiInterface
import okhttp3.MultipartBody

class RemoteDataSource constructor(private val apiService: ApiInterface){

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiInterface) : RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(apiService).apply { instance = this }
            }
    }

    suspend fun uploadImage(image: MultipartBody.Part): SkinImageResponse {
        return apiService.uploadImage(image)
    }

}