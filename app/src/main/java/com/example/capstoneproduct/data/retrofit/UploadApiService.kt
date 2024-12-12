package com.example.capstoneproduct.data.retrofit

import com.example.capstoneproduct.data.response.upload.investors.InvestorUploadResponse
import com.example.capstoneproduct.data.response.upload.umkm.UmkmUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadApiService {
    @Multipart
    @POST("investors")
    suspend fun uploadInvestors(
        @Part imgFile: MultipartBody.Part,
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
        @Part("phone_number") phoneNumber: RequestBody
    ): InvestorUploadResponse

    @Multipart
    @POST("umkm")
    suspend fun uploadUmkm(
        @Part imgFile: MultipartBody.Part,
        @Part("company_name") companyName: RequestBody,
        @Part("founding_date") foundingDate: RequestBody,
        @Part("founder_name") founderName: RequestBody,
        @Part("location") location: RequestBody,
        @Part("sector") sector: RequestBody,
        @Part("stage") stage: RequestBody,
        @Part("description") description: RequestBody,
        @Part("team_members") teamMembers: RequestBody,
        @Part("business_model") businessModel: RequestBody,
        @Part("loyal_customers") loyalCustomers: RequestBody,
        @Part("market_size") marketSize: RequestBody,
        @Part("market_target") marketTarget: RequestBody,
        @Part("amount_seeking") amountSeeking: RequestBody,
        @Part("funding_raised") fundingRaised: RequestBody,
        @Part("revenue") revenue: RequestBody,
        @Part("profitability") profitability: RequestBody,
        @Part("growth_rate") growthRate: RequestBody,
        @Part("preferred_investment_type") preferredInvestmentType: RequestBody,
        @Part("intended_use_of_funds") intendedUseOfFunds: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody
    ): UmkmUploadResponse
}