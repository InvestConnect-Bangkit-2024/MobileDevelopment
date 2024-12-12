package com.example.capstoneproduct.ui.upload.investor

import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.UploadRepository
import java.io.File

class UploadInvestorViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadInvestor(imageFile: File, investorName: String, location: String, investmentFocus: String, stages: String, thesis: String, totalDeals: Int, totalInvestments: Int, dealType: String, geographicFocus: String, criteria: String, phoneNumber: String) = repository.uploadInvestor (
        imageFile, investorName, location, investmentFocus, stages, thesis, totalDeals, totalInvestments, dealType, geographicFocus, criteria, phoneNumber
    )

}