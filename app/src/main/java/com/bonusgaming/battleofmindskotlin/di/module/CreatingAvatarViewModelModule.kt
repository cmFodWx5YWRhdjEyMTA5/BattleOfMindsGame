package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.login.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class CreatingAvatarViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreatingAvatarViewModel::class)
    @PerFragment
    abstract fun creatingAvatarViewModel(creatingAvatarViewModel: CreatingAvatarViewModel): ViewModel
}