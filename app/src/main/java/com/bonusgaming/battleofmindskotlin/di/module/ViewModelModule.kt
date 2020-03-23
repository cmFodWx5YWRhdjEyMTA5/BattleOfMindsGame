package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.MainViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.features.login.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.features.login.loading_assets.LoadingAssetsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadingAssetsViewModel::class)
    abstract fun loadingAssetsViewModel(viewModel: LoadingAssetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatingAvatarViewModel::class)
    abstract fun creatingAvatarViewModel(viewModel: CreatingAvatarViewModel): ViewModel
}