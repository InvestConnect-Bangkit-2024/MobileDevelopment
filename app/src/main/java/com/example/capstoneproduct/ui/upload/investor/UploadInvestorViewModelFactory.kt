package com.example.capstoneproduct.ui.upload.investor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection
import com.example.capstoneproduct.data.repository.UploadRepository

class UploadInvestorViewModelFactory(private val repository: UploadRepository) :
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UploadInvestorViewModel::class.java) -> {
                UploadInvestorViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: UploadInvestorViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: UploadInvestorViewModelFactory(Injection.provideUploadRepository(context))
            }.also { instance = it}
    }
}