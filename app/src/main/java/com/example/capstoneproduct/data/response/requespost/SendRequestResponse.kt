package com.example.capstoneproduct.data.response.requespost

import com.google.gson.annotations.SerializedName

data class SendRequestResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("investor_id")
	val investorId: String? = null
)
