package com.bangkit.scade.data.source

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.entity.*
import com.bangkit.scade.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface DataSource {

    suspend fun imageUpload(image: File): SkinImageResponse

    suspend fun getListEnglishArticle(): List<InformationEntity>

    suspend fun getListIndoensiaArticle(): List<InformationEntity>

    suspend fun getListHospital(): List<HospitalEntity>

    suspend fun getSearchHospital(query: String): List<HospitalEntity>

    suspend fun checkSession(token: String): SessionResponse

    suspend fun getDetailDiagnoses(token: String, id: Int): GetDiagnosesEntity

    suspend fun getDetailHospital(id: Int): HospitalEntity

    suspend fun createInvoice(invoiceData: InvoiceRequest): InvoiceResponse

    suspend fun login(loginData: LoginRequest): LoginResponse

    suspend fun register(registerData: RegisterRequest): RegisterResponse

    suspend fun createDiagnoses(
        token: String,
        name: String,
        image: File,
        position: String
    ): DiagnosesEntity

    fun getSessionToken(): LiveData<DataEntity>


    fun addDataCheck(data: DataEntity)

    fun removeDataCheck(data: DataEntity)

    fun checkDataExist(id: Int): LiveData<Boolean>


}