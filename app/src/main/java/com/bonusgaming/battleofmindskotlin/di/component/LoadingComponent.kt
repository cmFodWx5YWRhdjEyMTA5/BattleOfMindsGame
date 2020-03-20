package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.di.module.LoadingViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [LoadingViewModelModule::class])
interface LoadingComponent {
    fun inject(fragment: LoadingFragment)
}