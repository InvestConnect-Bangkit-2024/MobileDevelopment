package com.example.capstoneproduct.ui.umkm

import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.DealRepository

class DealUmkmViewModel(private val repository: DealRepository) : ViewModel() {

    suspend fun enterAmount(amount: Int, umkmId: String) = repository.enterAmount(amount, umkmId)

}