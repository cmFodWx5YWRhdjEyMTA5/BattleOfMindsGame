package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.main.vm.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MenuViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    @PerFragment
    abstract fun creatingMenuViewModel(menuViewModel: MenuViewModel): ViewModel
}