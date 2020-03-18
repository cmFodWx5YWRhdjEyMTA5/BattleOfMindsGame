package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.di.module.CreatingAvatarViewModelModule
import com.bonusgaming.battleofmindskotlin.di.module.LoadingViewModelModule
import com.bonusgaming.battleofmindskotlin.di.module.MenuViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.main.ui.MenuFragment
import com.bonusgaming.battleofmindskotlin.main.vm.MenuViewModel
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [LoadingViewModelModule::class])
interface LoadingComponent {
    fun inject(fragment: LoadingFragment)
}