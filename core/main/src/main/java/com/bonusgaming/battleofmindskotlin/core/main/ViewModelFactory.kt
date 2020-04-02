package com.bonusgaming.battleofmindskotlin.core.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider


open class ViewModelFactory constructor(
        private val viewModels: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass]
                ?: throw IllegalArgumentException("model class $modelClass not found >:")
        return viewModelProvider.get() as T
    }
}
