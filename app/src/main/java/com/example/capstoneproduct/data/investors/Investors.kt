package com.example.capstoneproduct.data.investors

import com.google.gson.annotations.SerializedName

data class Investors(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("investment_focus")
	val investmentFocus: String? = null,

	@field:SerializedName("investor_name")
	val investorName: String? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("deal_type")
	val dealType: String? = null,

	@field:SerializedName("criteria")
	val criteria: String? = null,

	@field:SerializedName("thesis")
	val thesis: String? = null,

	@field:SerializedName("total_deals")
	val totalDeals: Int? = null,

	@field:SerializedName("geographic_focus")
	val geographicFocus: String? = null,

	@field:SerializedName("stages")
	val stages: String? = null,

	@field:SerializedName("total_investments")
	val totalInvestments: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("investor_id")
	val investorId: String? = null
)
