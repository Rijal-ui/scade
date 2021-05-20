package com.bangkit.scade.data.source

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import java.io.File

interface DataSource {

    suspend fun imageUpload(image: File): SkinImageResponse

    fun getDataCheck(): LiveData<DataEntity>

    fun addDataCheck(data: DataEntity)

    fun removeDataCheck(data: DataEntity)

}