package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HospitalByIdResponse(

	@field:SerializedName("data")
	val data: DataHospitals? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataHospitals(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("invoices")
	val invoices: Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("DeletedAt")
	val deletedAt: Any? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null
)
