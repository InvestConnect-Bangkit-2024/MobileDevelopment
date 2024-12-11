package com.example.capstoneproduct.data

import com.example.capstoneproduct.data.investors.Data
import com.google.gson.annotations.SerializedName

data class DetailsInvestor(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("investment_focus")
	val investmentFocus: String? = null,

	@field:SerializedName("“img_url”")
	val imgUrl: String? = null,

	@field:SerializedName("deal_type")
	val dealType: String? = null,

	@field:SerializedName("criteria")
	val criteria: String? = null,

	@field:SerializedName("thesis")
	val thesis: String? = null,

	@field:SerializedName("total_deals")
	val totalDeals: String? = null,

	@field:SerializedName("geographic_focus")
	val geographicFocus: String? = null,

	@field:SerializedName("stages")
	val stages: String? = null,

	@field:SerializedName("total_investments")
	val totalInvestments: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("investor_id")
	val investorId: String? = null,

	@field:SerializedName("“investor_name”")
	val investorName: String? = null
)