package com.example.capstoneproduct.data.investors

import com.google.gson.annotations.SerializedName

data class RegisterInvestor(

	@field:SerializedName("deal_type")
	val dealType: String? = null,

	@field:SerializedName("criteria")
	val criteria: String? = null,

	@field:SerializedName("total_investments")
	val totalInvestments: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("investment_focus")
	val investmentFocus: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("investor_name")
	val investorName: String? = null,

	@field:SerializedName("thesis")
	val thesis: String? = null,

	@field:SerializedName("total_deals")
	val totalDeals: Int? = null,

	@field:SerializedName("geographic_focus")
	val geographicFocus: String? = null,

	@field:SerializedName("stages")
	val stages: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
