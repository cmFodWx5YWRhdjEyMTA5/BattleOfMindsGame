package com.bonusgaming.battleofmindskotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import javax.inject.Inject
import javax.inject.Provider

@PerFragment
class ViewModelFactory @Inject constructor(private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass]
                ?: throw IllegalArgumentException("model class $modelClass not found >:")
        return viewModelProvider.get() as T
    }
}