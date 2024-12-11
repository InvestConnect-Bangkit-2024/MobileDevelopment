package com.example.capstoneproduct.data.umkm

import com.google.gson.annotations.SerializedName

data class Umkm(

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

	@field:SerializedName("founder_name")
	val founderName: String? = null,

	@field:SerializedName("stage")
	val stage: String? = null,

	@field:SerializedName("amount_seeking")
	val amountSeeking: Int? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("intended_use_of_funds")
	val intendedUseOfFunds: String? = null,

	@field:SerializedName("founding_date")
	val foundingDate: String? = null,

	@field:SerializedName("sector")
	val sector: String? = null,

	@field:SerializedName("preferred_investment_type")
	val preferredInvestmentType: String? = null
)
