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

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("investment_request_id")
	val investmentRequestId: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("confirmed_date")
	val confirmedDate: Any? = null,

	@field:SerializedName("investor_id")
	val investorId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
