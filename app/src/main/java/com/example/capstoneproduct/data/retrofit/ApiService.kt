package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.DetailsInvestor
import com.example.capstoneproduct.data.investors.Investors
import com.example.capstoneproduct.data.umkm.Umkm
import retrofit2.http.GET

interface ApiService {
    @GET("investors")
    suspend fun getInvestor(): Investors

    @GET("umkm")
    suspend fun getUmkm(): Umkm

    @GET("DetailsInvestor")
    suspend fun getDetailInvestor(): DetailsInvestor

}