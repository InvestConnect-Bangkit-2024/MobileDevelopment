package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.response.login.ResponseLogin
import com.example.capstoneproduct.data.response.register.ResponseRegister
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun loginInvestor(
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseLogin
}