package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsViewModel
import com.bonusgaming.battleofmindskotlin.main.vm.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class LoadingAssetsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoadingAssetsViewModel::class)
    @PerFragment
    abstract fun creatingLoadingAssetsViewModel(loadingAssetsViewModel: LoadingAssetsViewModel): ViewModel
}