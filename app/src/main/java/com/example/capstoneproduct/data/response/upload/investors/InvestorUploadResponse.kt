package com.example.capstoneproduct.data.response.upload.investors

import com.google.gson.annotations.SerializedName

data class InvestorUploadResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("investor_id")
	val investorId: String? = null
)
