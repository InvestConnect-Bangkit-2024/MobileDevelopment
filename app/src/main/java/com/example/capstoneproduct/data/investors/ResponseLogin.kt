package com.example.capstoneproduct.data.investors

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null
)
