package com.example.capstoneproduct.data.response.upload.umkm

import com.example.capstoneproduct.data.response.upload.investors.Data
import com.google.gson.annotations.SerializedName

data class UmkmUploadResponse(

    @field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("umkm_id")
	val umkmId: String? = null
)
