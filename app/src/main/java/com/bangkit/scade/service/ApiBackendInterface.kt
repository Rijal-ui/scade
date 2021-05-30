package com.bangkit.scade.service

import com.bangkit.scade.data.source.remote.request.InvoiceRequest
import com.bangkit.scade.data.source.remote.request.LoginRequest
import com.bangkit.scade.data.source.remote.request.RegisterRequest
import com.bangkit.scade.data.source.remote.request.UpdateHospitalRequest
import com.bangkit.scade.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiBackendInterface {
    @GET("articles/english")
    suspend fun getArticleListEnglish(): ArticlesResponse

    @GET("articles/indonesia")
    suspend fun getArticleListIndonesia(): ArticlesResponse

    @GET("invoices")
    suspend fun getListInvoices(
        @Header("Authorization") token: String
    ): InvoicesListResponse

    @GET("hospitals")
    suspend fun getListHospital(): HospitalResponse

    @GET("hospitals/search")
    suspend fun getSearchHospital(
        @Query("region") city: String
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

    @GET("invoices/{invoice_id}")
    suspend fun getDetailInvoices(
        @Header("Authorization") token: String,
        @Path("invoice_id") id: Int
    ): InvoicesByIdResponse

    @POST("invoices/create")
    suspend fun createInvoice(
        @Header("Authorization") token: String,
        @Body invoiceData: InvoiceRequest
    ): InvoiceResponse

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

    @PUT("invoices/{ID}")
    suspend fun updateHispitalInvoice(
        @Header("Authorization") token: String,
        @Body updateHospitalData: UpdateHospitalRequest,
        @Path("ID") id: Int
    ): UpdateHospitalResponse

}