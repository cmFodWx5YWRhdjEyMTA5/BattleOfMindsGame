package com.bonusgaming.battleofmindskotlin.features.logo.di.components

import com.bonusgaming.battleofmindskotlin.ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.logo.di.module.LogoViewModelModule
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import dagger.Component

@PerFeature
@Component(dependencies = [UiComponent::class],
        modules = [LogoViewModelModule::class])
interface LogoComponent  {
    companion object {
        fun get(ui: UiComponent): LogoComponent {
            return DaggerLogoComponent.builder().uiComponent(ui).build()
        }
    }

    fun inject(fragment: HelloFragment)
}
