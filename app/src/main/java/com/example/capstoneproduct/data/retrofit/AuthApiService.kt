package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.investors.ResponseLogin
import com.example.capstoneproduct.data.response.register.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApiService {
    @Multipart
    @POST("/register")
    suspend fun registerInvestor(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("fullname") fullname: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("investor_name") investorName: RequestBody,
        @Part("location") location: RequestBody,
        @Part("investment_focus") investmentFocus: RequestBody,
        @Part("stages") stages: RequestBody,
        @Part("thesis") thesis: RequestBody,
        @Part("total_deals") totalDeals: RequestBody,
        @Part("total_investments") totalInvestments: RequestBody,
        @Part("deal_type") dealType: RequestBody,
        @Part("geographic_focus") geographicFocus: RequestBody,
        @Part("criteria") criteria: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun loginInvestor(
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseLogin
}