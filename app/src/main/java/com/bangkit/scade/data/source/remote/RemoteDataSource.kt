package com.bangkit.scade.data.source.remote

import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import com.bangkit.scade.data.source.remote.response.HospitalResponse
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import com.bangkit.scade.service.ApiMLInterface
import com.bangkit.scade.service.ApiBackendInterface
import okhttp3.MultipartBody

class RemoteDataSource constructor(
    private val apiMLService: ApiMLInterface,
    private val apiBackendService: ApiBackendInterface){

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiMLService: ApiMLInterface, apiBackendService: ApiBackendInterface
        ) : RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(apiMLService,apiBackendService).apply { instance = this }
            }
    }

    suspend fun uploadImage(image: MultipartBody.Part): SkinImageResponse {
        return apiMLService.uploadImage(image)
    }

    suspend fun getListEnglish(): ArticlesResponse {
        return  apiBackendService.getArticleListEnglish()
    }

    suspend fun getListIndonesia(): ArticlesResponse {
        return apiBackendService.getArticleListIndonesia()
    }

    suspend fun getListHospital(): HospitalResponse {
        return apiBackendService.getListHospital()
    }

}