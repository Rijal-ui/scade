package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SkinImageResponse(
    @field:SerializedName("data")
    val data: List<String>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)