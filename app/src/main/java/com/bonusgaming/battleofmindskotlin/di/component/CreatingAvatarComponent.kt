package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.login.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.di.module.CreatingAvatarViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [CreatingAvatarViewModelModule::class])
interface CreatingAvatarComponent {
    fun inject(fragment: CreatingAvatarFragment)
}