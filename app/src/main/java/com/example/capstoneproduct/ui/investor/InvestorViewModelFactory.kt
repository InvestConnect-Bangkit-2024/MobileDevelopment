package com.example.capstoneproduct.ui.investor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection
import com.example.capstoneproduct.data.repository.InvestorRepository

class InvestorViewModelFactory(private val repository: InvestorRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvestorViewModel::class.java)) {
            return InvestorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var INSTANCE: InvestorViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): InvestorViewModelFactory {
            if (INSTANCE == null) {
                synchronized(InvestorViewModelFactory::class.java) {
                    INSTANCE = InvestorViewModelFactory(Injection.provideInvestorRepository(context))
                }
            }
            return INSTANCE as InvestorViewModelFactory
        }
    }
}