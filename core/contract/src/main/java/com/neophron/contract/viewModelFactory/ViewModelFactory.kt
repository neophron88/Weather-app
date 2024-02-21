package com.neophron.contract.viewModelFactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neophron.contract.extractDependency

class ViewModelFactory<VM : ViewModel>(
    private val provideViewModel: () -> VM
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provideViewModel() as T
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    noinline provideViewModel: () -> VM
) = viewModels<VM> { ViewModelFactory(provideViewModel) }

inline fun <reified T> Fragment.extract(): T =
    requireActivity().application.extractDependency()