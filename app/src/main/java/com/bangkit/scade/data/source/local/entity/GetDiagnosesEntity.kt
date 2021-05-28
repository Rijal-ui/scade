package com.bangkit.scade.data.source.local.entity

import com.google.gson.annotations.SerializedName

data class GetDiagnosesEntity(
    val id: Int? = null,
    val cancerName: String? = null,
    val cancerImage: String? = null,
    val position: String? = null,
    val createdAt: String? = null
)
