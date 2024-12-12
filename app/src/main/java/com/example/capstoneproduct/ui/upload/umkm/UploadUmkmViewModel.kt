package com.example.capstoneproduct.ui.upload.umkm

import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.UploadRepository
import java.io.File

class UploadUmkmViewModel(private val repository: UploadRepository): ViewModel() {
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
    ) = repository.uploadUmkm(
        imageFile,
        companyName,
        foundingDate,
        founderName,
        location,
        sector,
        stage,
        description,
        teamMembers,
        businessModel,
        loyalCustomers,
        marketSize,
        marketTarget,
        amountSeeking,
        fundingRaised,
        revenue,
        profitability,
        growthRate,
        preferredInvestmentType,
        intendedUseOfFunds,
        phoneNumber
    )

}