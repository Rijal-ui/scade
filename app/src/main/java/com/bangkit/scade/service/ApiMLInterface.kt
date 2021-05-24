package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiMLInterface {

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): SkinImageResponse


}