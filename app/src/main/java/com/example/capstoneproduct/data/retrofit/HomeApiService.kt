package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.response.home.HomeResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("recommend")
    suspend fun getRecommend(): HomeResponse
}