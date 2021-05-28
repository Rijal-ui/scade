package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiBackendInterface {
    @GET("articles/english")
    suspend fun getArticleListEnglish(): ArticlesResponse

    @GET("articles/indonesia")
    suspend fun getArticleListIndonesia(): ArticlesResponse

    @GET("hospitals")
    suspend fun getListHospital(): HospitalResponse

    @GET("hospitals/search")
    suspend fun getSearchHospital(
        @Query("city") city: String
    ): HospitalResponse

    @GET("article_languages")
    suspend fun checkSession(
        @Header("Authorization") token: String
    ): SessionResponse

    @GET("diagnoses/{ID}")
    suspend fun getDetailDiagnoses(
        @Header("Authorization") token: String,
        @Path("ID") id: Int
    ): DiagnosesByIdResponse

    @GET("hospitals/{ID}")
    suspend fun getDetailHospital(@Path("ID") id: Int): HospitalByIdResponse

    @POST
    suspend fun createInvoice(@Body invoiceData: InvoiceRequest): InvoiceResponse

    @POST("auth/sign_in")
    suspend fun login(@Body loginData: LoginRequest): LoginResponse

    @POST("auth/sign_up")
    suspend fun register(@Body registerData: RegisterRequest): RegisterResponse

    @Multipart
    @POST("diagnoses/create")
    suspend fun createDiagnoses(
        @Header("Authorization") token: String,
        @Part("cancer_name") cancerName: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("position") position: RequestBody
    ): DiagnosesResponse
}