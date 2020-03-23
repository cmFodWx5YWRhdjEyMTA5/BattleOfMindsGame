package com.bonusgaming.battleofmindskotlin.features.login.di.component

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.DaggerUiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import dagger.Component

@Component(dependencies = [AppFacade::class, DbApi::class])
interface LoginComponent {
    companion object {
        private var loginComponent: LoginComponent? = null

        fun get(appFacade: AppFacade): LoginComponent {
            return loginComponent ?: DaggerLoginComponent.builder().appFacade(appFacade).build()
                    .also {
                        loginComponent = it
                    }
        }
    }
}