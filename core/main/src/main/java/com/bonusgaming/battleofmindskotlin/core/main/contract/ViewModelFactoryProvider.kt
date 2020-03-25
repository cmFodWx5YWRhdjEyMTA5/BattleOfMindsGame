package com.bonusgaming.battleofmindskotlin.core.main.contract

import androidx.lifecycle.ViewModel
import javax.inject.Provider

interface ViewModelFactoryProvider {
    fun provide(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
}