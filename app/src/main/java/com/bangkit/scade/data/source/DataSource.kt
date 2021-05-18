package com.bangkit.scade.data.source

import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import java.io.File

interface DataSource {

    suspend fun imageUpload(image: File): SkinImageResponse

}