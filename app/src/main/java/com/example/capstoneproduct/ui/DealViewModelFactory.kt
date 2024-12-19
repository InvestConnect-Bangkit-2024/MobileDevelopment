package com.example.capstoneproduct.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection
import com.example.capstoneproduct.data.repository.DealRepository
import com.example.capstoneproduct.ui.investor.DealInvestorViewModel
import com.example.capstoneproduct.ui.umkm.DealUmkmViewModel

class DealViewModelFactory(private val repository: DealRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DealUmkmViewModel::class.java) -> {
                DealUmkmViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DealInvestorViewModel::class.java) -> {
                DealInvestorViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DealViewModelFactory? = null
        fun getInstance(context: Context): DealViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DealViewModelFactory::class.java) {
                    INSTANCE = DealViewModelFactory(Injection.provideDealRepository(context))
                }
            }
            return INSTANCE as DealViewModelFactory
        }
    }

}