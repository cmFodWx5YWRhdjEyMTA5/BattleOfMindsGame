package com.bonusgaming.battleofmindskotlin.features.menu.di.component

import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.ui.MenuFragment
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.vm.MenuViewModelModule
import dagger.Component

@PerFeature
@Component(dependencies =
[UiComponent::class, AppFacade::class, DbApi::class, WebApi::class],
        modules = [MenuViewModelModule::class])
interface MenuComponent {
    companion object {
        fun get(appFacade: AppFacade, ui: UiComponent, web: WebApi, db: DbApi): MenuComponent {
            return DaggerMenuComponent.builder()
                    .appFacade(appFacade)
                    .uiComponent(ui)
                    .webApi(web)
                    .dbApi(db)
                    .build()
        }
    }

    fun inject(fragment: MenuFragment)
}
