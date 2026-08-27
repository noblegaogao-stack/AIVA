package com.noble.aiva.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noble.aiva.domain.usecase.StartRecordingUseCase
import com.noble.aiva.feature.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val useCase: StartRecordingUseCase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(useCase) as T
    }
}
