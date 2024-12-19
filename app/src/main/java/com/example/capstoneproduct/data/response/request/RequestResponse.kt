package com.example.capstoneproduct.data.response.request

import com.google.gson.annotations.SerializedName

data class RequestResponse(

	@field:SerializedName("data")
	val data: List<DataItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("umkm_id")
	val umkmId: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("investment_offering_id")
	val investmentOfferingId: String? = null,

	@field:SerializedName("confirmed_date")
	val confirmedDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
