package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
