package com.example.capstoneproduct.data.repository

import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.response.investmentsreq.InvestmentsRequestResponse
import com.example.capstoneproduct.data.retrofit.DealApiService

class DealRepository(
    private val dealApiService: DealApiService
) {

    suspend fun enterAmount(amount: Int): InvestmentsRequestResponse {
        return dealApiService.addInvestmentRequest(amount)
    }

    companion object{
        @Volatile
        private var instance: DealRepository? = null
        fun getInstance(
            dealApiService: DealApiService,
            userPreference: UserPreference
        ): DealRepository =
            instance ?: synchronized(this) {
                instance ?: DealRepository(dealApiService)
            }.also { instance = it }
    }

}