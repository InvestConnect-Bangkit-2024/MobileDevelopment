package com.example.capstoneproduct.data.response.home

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("companies")
	val companies: List<CompaniesItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CompaniesItem(

	@field:SerializedName("imgURL")
	val imgURL: String? = null,

	@field:SerializedName("stage")
	val stage: String? = null,

	@field:SerializedName("loyal_customers")
	val loyalCustomers: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("business_model")
	val businessModel: String? = null,

	@field:SerializedName("sector")
	val sector: String? = null
)
