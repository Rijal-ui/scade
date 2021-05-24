package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DataHospital (

    @field:SerializedName("ID")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String? = null,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("DeletedAt")
    val deletedAt: Any? = null
)