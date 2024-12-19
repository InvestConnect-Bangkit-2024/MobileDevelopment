package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.response.investmentsreq.InvestmentsRequestResponse
import com.example.capstoneproduct.data.response.requespost.SendRequestResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DealApiService {
    @FormUrlEncoded
    @POST("investments/offerings")
    suspend fun addInvestmentRequest(
        @Field("amount") amount: Int,
        @Field("umkm_id") umkmId: String,
        ): InvestmentsRequestResponse

    @FormUrlEncoded
    @POST("investments/requests")
    suspend fun addInvestInvestor(
        @Field("amount") amount: Int,
        @Field("investor_id") investorId: String
    ): SendRequestResponse

}