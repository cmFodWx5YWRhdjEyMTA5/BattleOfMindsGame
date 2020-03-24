package com.bonusgaming.battleofmindskotlin.base_ui.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import javax.inject.Inject
import javax.inject.Provider

@PerFacade
class MainViewModelFactory
@Inject constructor(
        private val viewModels: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelFactory(viewModels)