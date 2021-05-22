package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import retrofit2.http.GET

interface ApiBackendInterface {
    @GET("articles/english")
    suspend fun getArticleListEnglish() : ArticlesResponse

    @GET("articles/indonesia")
    suspend fun getArticleListIndonesia() : ArticlesResponse
}