package com.bonusgaming.battleofmindskotlin.base_ui.di.component

import com.bonusgaming.battleofmindskotlin.base_ui.di.module.MainViewModelModule
import com.bonusgaming.battleofmindskotlin.base_ui.di.module.ResourcesModule
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.MainActivityView
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.ResourcesProvider
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import dagger.Component

@PerFacade
@Component(dependencies = [AppFacade::class], modules = [ResourcesModule::class, MainViewModelModule::class])
interface UiComponent : ResourcesProvider {
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