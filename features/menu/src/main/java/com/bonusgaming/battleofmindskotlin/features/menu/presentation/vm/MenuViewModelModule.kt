package com.bonusgaming.battleofmindskotlin.features.menu.presentation.vm

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MenuViewModelModule {

    @PerFeature
    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    fun menuViewModel(viewModel: MenuViewModel): ViewModel

}
