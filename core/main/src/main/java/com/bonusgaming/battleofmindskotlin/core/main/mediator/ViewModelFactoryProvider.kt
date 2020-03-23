package com.bonusgaming.battleofmindskotlin.core.main.mediator

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.ViewModelFactory
import javax.inject.Provider

interface ViewModelFactoryProvider {
    fun provide(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
}