package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.response.investmentsreq.InvestmentsRequestResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DealApiService {
    @FormUrlEncoded
    @POST("investors/requests")
    suspend fun addInvestmentRequest(
        @Field("amount") amount: Int
    ): InvestmentsRequestResponse



}