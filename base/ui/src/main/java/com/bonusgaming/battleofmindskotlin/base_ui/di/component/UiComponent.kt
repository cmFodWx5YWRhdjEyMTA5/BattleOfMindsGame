package com.bonusgaming.battleofmindskotlin.base_ui.di.component

import com.bonusgaming.battleofmindskotlin.base_ui.di.module.ResourcesModule
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.MainActivityView
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [AppFacade::class], modules = [ResourcesModule::class])
interface UiComponent {
    companion object {
        private var appComponent: UiComponent? = null

        fun get(appFacade: AppFacade): UiComponent {
            return appComponent ?: DaggerUiComponent.builder().appFacade(appFacade).build()
                    .also {
                        appComponent = it
                    }
        }
    }


    fun inject(mainActivity: MainActivityView)
}