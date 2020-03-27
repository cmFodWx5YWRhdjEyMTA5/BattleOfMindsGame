package com.bonusgaming.battleofmindskotlin.features.logo.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import javax.inject.Inject
import javax.inject.Provider

@PerFeature
class LoadingViewModelFactory
@Inject constructor(
        private val viewModels: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelFactory(viewModels)
