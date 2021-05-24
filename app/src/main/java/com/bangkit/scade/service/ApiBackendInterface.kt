package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiBackendInterface {
    @GET("articles/english")
    suspend fun getArticleListEnglish() : ArticlesResponse

    @GET("articles/indonesia")
    suspend fun getArticleListIndonesia() : ArticlesResponse

    @GET("hospitals")
    suspend fun getListHospital(): HospitalResponse

    @GET("article_languages")
    suspend fun checkSession(
        @Header("Authorization") token: String
    ) : SessionResponse

    @POST("auth/sign_in")
    suspend fun signIn(@Body loginData: LoginRequest): LoginResponse
}