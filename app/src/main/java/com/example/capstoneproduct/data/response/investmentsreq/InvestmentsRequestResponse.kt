package com.example.capstoneproduct.data.response.investmentsreq

import com.google.gson.annotations.SerializedName

data class InvestmentsRequestResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("investment_request_id")
	val investmentRequestId: String? = null
)
