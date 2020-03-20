package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.di.module.LoadingAssetsViewModelModule
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.login.loading_assets.LoadingAssetsFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [LoadingAssetsViewModelModule::class])
interface LoadingAssetsComponent {
    fun inject(fragment: LoadingAssetsFragment)
}