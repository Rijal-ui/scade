package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateHospitalResponse(

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
