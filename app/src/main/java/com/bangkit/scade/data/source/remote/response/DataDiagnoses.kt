package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DataDiagnoses(
    @field:SerializedName("ID")
    val id: Int? = null,

    @field:SerializedName("cancer_name")
    val cancerName: String? = null,

    @field:SerializedName("cancer_image")
    val cancerImage: String? = null,

    @field:SerializedName("position")
    val position: String? = null,

    @field:SerializedName("price")
    val price: String? = null,

    @field:SerializedName("user_id")
    val user_id: Int? = null,

    @field:SerializedName("invoices")
    val invoices: Any? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String? = null,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("DeletedAt")
    val deletedAt: Any? = null
)
