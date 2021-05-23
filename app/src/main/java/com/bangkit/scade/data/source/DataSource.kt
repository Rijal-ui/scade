package com.bangkit.scade.data.source

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import com.bangkit.scade.data.source.remote.response.SessionResponse
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import java.io.File

interface DataSource {

    suspend fun imageUpload(image: File): SkinImageResponse

    suspend fun getListEnglishArticle() : List<InformationEntity>

    suspend fun getListIndoensiaArticle() : List<InformationEntity>

    suspend fun checkSession(token: String): SessionResponse

    fun getDataCheck(): LiveData<DataEntity>

    fun addDataCheck(data: DataEntity)

    fun removeDataCheck(data: DataEntity)

    fun checkDataExist(id: Int): Boolean



}