package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiMLInterface {

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): SkinImageResponse


}