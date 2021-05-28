package com.bangkit.scade.data.source.local.entity

import com.google.gson.annotations.SerializedName

data class GetDiagnosesEntity(
    val id: Int? = null,
    val cancerName: String? = null,
    val cancerImage: String? = null,
    val position: String? = null,
    val price: Int? = null,
    val user_id: Int? = null,
    val invoices: Any? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: Any? = null
)
