package com.bonusgaming.battleofmindskotlin.features.logo.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LogoViewModelModule {

    @PerFeature
    @Binds
    @IntoMap
    @ViewModelKey(HelloViewModel::class)
    fun helloViewModel(viewModel: HelloViewModel): ViewModel

}
