package com.bangkit.scade.data.source

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.LocalDataSource
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.RemoteDataSource
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import com.bangkit.scade.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            remoteData: RemoteDataSource, localDataSource: LocalDataSource, appExecutors: AppExecutors
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
}