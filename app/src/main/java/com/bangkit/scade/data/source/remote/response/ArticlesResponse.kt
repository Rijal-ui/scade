package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

