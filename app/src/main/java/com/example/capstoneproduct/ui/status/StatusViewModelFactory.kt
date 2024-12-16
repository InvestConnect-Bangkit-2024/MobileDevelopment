package com.example.capstoneproduct.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproduct.data.di.Injection
import com.example.capstoneproduct.data.repository.StatusRepository
import com.example.capstoneproduct.ui.status.StatusViewModel


class StatusViewModelFactory(private val repository: StatusRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StatusViewModel::class.java) -> {
                StatusViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StatusViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): StatusViewModelFactory {
            if (INSTANCE == null) {
                synchronized(StatusViewModelFactory::class.java) {
                    INSTANCE = StatusViewModelFactory(Injection.provideStatusRepository(context))
                }
            }
            return INSTANCE as StatusViewModelFactory
        }
    }

}