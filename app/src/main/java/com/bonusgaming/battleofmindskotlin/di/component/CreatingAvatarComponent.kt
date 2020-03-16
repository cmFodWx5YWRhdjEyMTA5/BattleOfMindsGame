package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.di.module.ViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [ViewModelModule::class])
interface CreatingAvatarComponent {
    fun inject(fragment: CreatingAvatarFragment)

   /* @Subcomponent.Builder
    interface Builder {
        fun viewModelModule(module: ViewModelModule): CreatingAvatarComponent.Builder
        fun build(): CreatingAvatarComponent
    }*/
}