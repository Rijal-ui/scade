package com.bangkit.scade.data.source

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.LocalDataSource
import com.bangkit.scade.data.source.local.entity.*
import com.bangkit.scade.data.source.remote.RemoteDataSource
import com.bangkit.scade.data.source.remote.response.*
import com.bangkit.scade.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class Repository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData, localDataSource, appExecutors).apply { instance = this }
            }
    }

    override suspend fun imageUpload(image: File): SkinImageResponse {
        return withContext(Dispatchers.IO) {
            val payload = RequestBody.create("image/*".toMediaTypeOrNull(), image)
            val body = MultipartBody.Part.createFormData("skin_image", image.name, payload)
            remoteDataSource.uploadImage(body)
        }
    }

    override suspend fun getListEnglishArticle(): List<InformationEntity> {
        val result = withContext(Dispatchers.IO) { (remoteDataSource.getListEnglish()) }
        val listArticle = ArrayList<InformationEntity>()
        result.data?.forEach { response ->
            if (response != null) {
                val information = InformationEntity(
                    thumbnail = response.thumbnail,
                    createdAt = response.createdAt,
                    iD = response.iD,
                    deletedAt = response.deletedAt,
                    title = response.title,
                    body = response.body,
                    articleLanguageId = response.articleLanguageId,
                    updatedAt = response.updatedAt
                )
                listArticle.add(information)
            }
        }
        return listArticle
    }

    override suspend fun getListIndoensiaArticle(): List<InformationEntity> {
        val result = withContext(Dispatchers.IO) { (remoteDataSource.getListIndonesia()) }
        val listArticle = ArrayList<InformationEntity>()
        result.data?.forEach { response ->
            if (response != null) {
                val information = InformationEntity(
                    thumbnail = response.thumbnail,
                    createdAt = response.createdAt,
                    iD = response.iD,
                    deletedAt = response.deletedAt,
                    title = response.title,
                    body = response.body,
                    articleLanguageId = response.articleLanguageId,
                    updatedAt = response.updatedAt
                )
                listArticle.add(information)
            }
        }
        return listArticle
    }

    override suspend fun getListHospital(): List<HospitalEntity> {
        val result = withContext(Dispatchers.IO) { (remoteDataSource.getListHospital()) }
        val listHospital = ArrayList<HospitalEntity>()
        result.data?.forEach { response ->
            if (response != null) {
                val hospital = HospitalEntity(
                    id = response.id,
                    name = response.name,
                    address = response.address,
                    phone = response.phone,
                    city = response.city,
                    province = response.province,
                    createdAt = response.createdAt,
                    deletedAt = response.deletedAt,
                    updatedAt = response.updatedAt
                )
                listHospital.add(hospital)
            }
        }
        return listHospital
    }

    override suspend fun getSearchHospital(query: String): List<HospitalEntity> {
        val result = withContext(Dispatchers.IO) { (remoteDataSource.getSearchHospital(query)) }
        val listHospital = ArrayList<HospitalEntity>()
        result.data?.forEach { response ->
            if (response != null) {
                val hospital = HospitalEntity(
                    id = response.id,
                    name = response.name,
                    address = response.address,
                    phone = response.phone,
                    city = response.city,
                    province = response.province,
                    createdAt = response.createdAt,
                    deletedAt = response.deletedAt,
                    updatedAt = response.updatedAt
                )
                listHospital.add(hospital)
            }
        }
        return listHospital
    }

    override suspend fun checkSession(token: String): SessionResponse {
        return withContext(Dispatchers.IO) { remoteDataSource.checkSession(token) }
    }

    override suspend fun getDetailDiagnoses(id: Int): GetDiagnosesEntity {
        val result = remoteDataSource.getDetailDiagnoses(id)
        return GetDiagnosesEntity(
            id = result.data?.iD,
            cancerName = result.data?.cancerName,
            cancerImage = result.data?.cancerImage,
            position = result.data?.position,
            price = result.data?.price,
            user_id = result.data?.userId,
            invoices = result.data?.invoices,
            createdAt = result.data?.createdAt,
            updatedAt = result.data?.updatedAt,
            deletedAt = result.data?.deletedAt
        )
    }

    override suspend fun getDetailHospital(id: Int): HospitalEntity {
        val result = remoteDataSource.getDetailHospital(id)
        return HospitalEntity(
            id = result.data?.iD,
            name = result.data?.name,
            address = result.data?.address,
            phone = result.data?.phone,
            city = result.data?.city,
            province = result.data?.province,
            createdAt = result.data?.createdAt,
            updatedAt = result.data?.updatedAt,
            deletedAt = result.data?.deletedAt
        )
    }

    override suspend fun login(loginData: LoginRequest): LoginResponse {
        return withContext(Dispatchers.IO) { remoteDataSource.login(loginData) }
    }

    override suspend fun register(registerData: RegisterRequest): RegisterResponse {
        return withContext(Dispatchers.IO) { remoteDataSource.register(registerData) }
    }


    override suspend fun createDiagnoses(
        token: String,
        name: String,
        image: File,
        position: String
    ): DiagnosesEntity {

        val result = withContext(Dispatchers.IO) {
            val cancerName = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val payload = RequestBody.create("image/*".toMediaTypeOrNull(), image)
            val position = position.toRequestBody("text/plain".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData("cancer_image", image.name, payload)
            remoteDataSource.createDiagnoses(
                token,
                cancerName,
                image,
                position
            )
        }
        return DiagnosesEntity(result.data)
    }


    override fun getDataCheck(): LiveData<DataEntity> {
        return localDataSource.getDataCheck()
    }

    override fun addDataCheck(data: DataEntity) {
        appExecutors.diskIO().execute {
            localDataSource.insertDataCheck(data)
        }
    }

    override fun removeDataCheck(data: DataEntity) {
        appExecutors.diskIO().execute {
            localDataSource.deleteDataCheck(data)
        }
    }

    override fun checkDataExist(id: Int): LiveData<Boolean> {
        return localDataSource.checkDataExist(id)
    }


}