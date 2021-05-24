package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import com.bangkit.scade.data.source.remote.response.HospitalResponse
import com.bangkit.scade.data.source.remote.response.SessionResponse
import retrofit2.http.GET
import retrofit2.http.Header

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
}