package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HospitalResponse(

    @field:SerializedName("data")
    val data: List<DataHospital?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
