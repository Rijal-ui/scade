package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DiagnosesByIdResponse(

	@field:SerializedName("data")
	val data: DataDiagnose? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataDiagnose(

	@field:SerializedName("invoices")
	val invoices: Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("cancer_name")
	val cancerName: String? = null,

	@field:SerializedName("cancer_image")
	val cancerImage: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("DeletedAt")
	val deletedAt: Any? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null
)
