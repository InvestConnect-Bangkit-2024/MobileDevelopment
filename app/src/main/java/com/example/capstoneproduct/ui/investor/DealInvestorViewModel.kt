package com.example.capstoneproduct.ui.investor

import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.DealRepository

class DealInvestorViewModel(private val repository: DealRepository) : ViewModel() {
    suspend fun amountRequest(amount: Int, investorId: String) = repository.amountInvestor(amount, investorId)
}