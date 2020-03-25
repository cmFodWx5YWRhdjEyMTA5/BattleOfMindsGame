package com.bonusgaming.battleofmindskotlin.features.logo.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.loading.LoadingAssetsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoadingAssetsViewModelModule {

    @PerFeature
    @Binds
    @IntoMap
    @ViewModelKey(LoadingAssetsViewModel::class)
    fun loadingAssetsViewModel(viewModel: LoadingAssetsViewModel): ViewModel

}