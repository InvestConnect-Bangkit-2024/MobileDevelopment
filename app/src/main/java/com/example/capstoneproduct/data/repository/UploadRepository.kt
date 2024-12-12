package com.example.capstoneproduct.data.repository

import androidx.lifecycle.liveData
import com.example.capstoneproduct.data.pref.UserPreference
import com.example.capstoneproduct.data.response.upload.investors.InvestorUploadResponse
import com.example.capstoneproduct.data.response.upload.umkm.UmkmUploadResponse
import com.example.capstoneproduct.data.retrofit.UploadApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UploadRepository(
    private val uploadApiService: UploadApiService,
    private val userPreference: UserPreference
) {
    fun uploadInvestor(
        imageFile: File,
        investorName: String,
        location: String,
        investmentFocus: String,
        stages: String,
        thesis: String,
        totalDeals: Int,
        totalInvestments: Int,
        dealType: String,
        geographicFocus: String,
        criteria: String,
        phoneNumber: String
    ) = liveData {
        emit(Result.Loading)

        val investorNameBody = investorName.toRequestBody("text/plain".toMediaType())
        val locationBody = location.toRequestBody("text/plain".toMediaType())
        val investmentFocusBody = investmentFocus.toRequestBody("text/plain".toMediaType())
        val stagesBody = stages.toRequestBody("text/plain".toMediaType())
        val thesisBody = thesis.toRequestBody("text/plain".toMediaType())
        val dealTypeBody = dealType.toRequestBody("text/plain".toMediaType())
        val geographicFocusBody = geographicFocus.toRequestBody("text/plain".toMediaType())
        val criteriaBody = criteria.toRequestBody("text/plain".toMediaType())
        val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaType())

        val totalDealsBody = totalDeals.toString().toRequestBody("text/plain".toMediaType())
        val totalInvestmentsBody = totalInvestments.toString().toRequestBody("text/plain".toMediaType())

        val requestImageFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("img_file", imageFile.name, requestImageFile)

        try {
            val successResponse = uploadApiService.uploadInvestors(
                multipartBody,
                investorNameBody,
                locationBody,
                investmentFocusBody,
                stagesBody,
                thesisBody,
                totalDealsBody,
                totalInvestmentsBody,
                dealTypeBody,
                geographicFocusBody,
                criteriaBody,
                phoneNumberBody
            )

            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, InvestorUploadResponse::class.java)
            emit(errorResponse.message?.let { Result.Error(it) })
        }


    }

    fun uploadUmkm(
        imageFile: File,
        companyName: String,
        foundingDate: String,
        founderName: String,
        location: String,
        sector: String,
        stage: String,
        description: String,
        teamMembers: Int,
        businessModel: String,
        loyalCustomers: Int,
        marketSize: Int,
        marketTarget: String,
        amountSeeking: Int,
        fundingRaised: Int,
        revenue: Int,
        profitability: Float,
        growthRate: Int,
        preferredInvestmentType: String,
        intendedUseOfFunds: String,
        phoneNumber: String
    ) = liveData {
        emit(Result.Loading)

        val companyNameBody = companyName.toRequestBody("text/plain".toMediaType())
        val foundingDateBody = foundingDate.toRequestBody("text/plain".toMediaType())
        val founderNameBody = founderName.toRequestBody("text/plain".toMediaType())
        val locationBody = location.toRequestBody("text/plain".toMediaType())
        val sectorBody = sector.toRequestBody("text/plain".toMediaType())
        val stageBody = stage.toRequestBody("text/plain".toMediaType())
        val descriptionBody = description.toRequestBody("text/plain".toMediaType())
        val businessModelBody = businessModel.toRequestBody("text/plain".toMediaType())
        val marketTargetBody = marketTarget.toRequestBody("text/plain".toMediaType())
        val intendedUseOfFundsBody = intendedUseOfFunds.toRequestBody("text/plain".toMediaType())
        val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaType())
        val preferredInvestmentTypeBody = preferredInvestmentType.toRequestBody("text/plain".toMediaType())

        val teamMembersBody = teamMembers.toString().toRequestBody("text/plain".toMediaType())
        val loyalCustomersBody = loyalCustomers.toString().toRequestBody("text/plain".toMediaType())
        val marketSizeBody = marketSize.toString().toRequestBody("text/plain".toMediaType())
        val amountSeekingBody = amountSeeking.toString().toRequestBody("text/plain".toMediaType())
        val fundingRaisedBody = fundingRaised.toString().toRequestBody("text/plain".toMediaType())
        val revenueBody = revenue.toString().toRequestBody("text/plain".toMediaType())
        val growthRateBody = growthRate.toString().toRequestBody("text/plain".toMediaType())

        val profitabilityBody = profitability.toString().toRequestBody("text/plain".toMediaType())

        val requestImageFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("img_file", imageFile.name, requestImageFile)

        try {
            val successResponse = uploadApiService.uploadUmkm(
                multipartBody,
                companyNameBody,
                foundingDateBody,
                founderNameBody,
                locationBody,
                sectorBody,
                stageBody,
                descriptionBody,
                teamMembersBody,
                businessModelBody,
                loyalCustomersBody,
                marketSizeBody,
                marketTargetBody,
                amountSeekingBody,
                fundingRaisedBody,
                revenueBody,
                profitabilityBody,
                growthRateBody,
                preferredInvestmentTypeBody,
                intendedUseOfFundsBody,
                phoneNumberBody
            )

            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UmkmUploadResponse::class.java)
            emit(errorResponse.message?.let { Result.Error(it) })
        }
    }

    companion object{
        @Volatile
        private var instance: UploadRepository? = null

        fun getInstance(uploadApiService: UploadApiService, userPreference: UserPreference): UploadRepository =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(uploadApiService, userPreference)
            }.also { instance = it}
    }

}