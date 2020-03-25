package com.bonusgaming.battleofmindskotlin.features.creating_avatar.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.creating_avatar.CreatingAvatarViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CreatingAvatarViewModelModule {

    @PerFeature
    @Binds
    @IntoMap
    @ViewModelKey(CreatingAvatarViewModel::class)
    fun creatingAvatarViewModel(viewModel: CreatingAvatarViewModel): ViewModel

}