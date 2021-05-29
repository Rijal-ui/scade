package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class InvoicesByIdResponse(

	@field:SerializedName("data")
	val data: DataInvoice? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataInvoice(

	@field:SerializedName("cancer_position")
	val cancerPosition: String? = null,

	@field:SerializedName("invoice_created_at")
	val invoiceCreatedAt: String? = null,

	@field:SerializedName("invoice_updated_at")
	val invoiceUpdatedAt: String? = null,

	@field:SerializedName("hospital_province")
	val hospitalProvince: String? = null,

	@field:SerializedName("cancer_name")
	val cancerName: String? = null,

	@field:SerializedName("hospital_phone")
	val hospitalPhone: String? = null,

	@field:SerializedName("cancer_image")
	val cancerImage: String? = null,

	@field:SerializedName("invoice_id")
	val invoiceId: Int? = null,

	@field:SerializedName("hospital_city")
	val hospitalCity: String? = null,

	@field:SerializedName("hospital_address")
	val hospitalAddress: String? = null,

	@field:SerializedName("hospital_name")
	val hospitalName: String? = null
)
