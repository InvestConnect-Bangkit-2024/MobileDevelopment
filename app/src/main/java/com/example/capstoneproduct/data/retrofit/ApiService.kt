package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.DetailsInvestor
import com.example.capstoneproduct.data.investors.Investors
import com.example.capstoneproduct.data.response.home.HomeResponse
import com.example.capstoneproduct.data.response.profile.ProfileResponse
import com.example.capstoneproduct.data.response.request.RequestResponse
import com.example.capstoneproduct.data.umkm.Umkm
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("investors")
    suspend fun getInvestor(
    ): Investors

    @GET("umkm")
    suspend fun getUmkm(): Umkm

    @GET("investments/offerings/sent")
    suspend fun getOffers(): RequestResponse

    @GET("user/profile") // Adjust the endpoint as necessary
    suspend fun getUser (): ProfileResponse

}