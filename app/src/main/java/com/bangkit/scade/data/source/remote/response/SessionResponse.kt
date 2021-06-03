package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @field:SerializedName("message")
    val message: String? = null
)
