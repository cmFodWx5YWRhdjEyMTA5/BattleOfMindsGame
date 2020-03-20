package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.di.module.MenuViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.main.ui.MenuFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [MenuViewModelModule::class])
interface MenuComponent {
    fun inject(fragment: MenuFragment)
}