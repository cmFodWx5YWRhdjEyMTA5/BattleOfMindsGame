package com.bonusgaming.battleofmindskotlin.features.login.di.component

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.features.login.creating_avatar.CreatingAvatarFragment
import dagger.Component

@PerFeature
@Component(dependencies = [AppFacade::class, DbApi::class, WebApi::class, UiComponent::class])
interface LoginComponent {
    companion object {
        private var loginComponent: LoginComponent? = null

        fun get(uiComponent: UiComponent, dbApi: DbApi, webApi: WebApi): LoginComponent {
            return loginComponent ?: DaggerLoginComponent.builder().uiComponent(uiComponent)
                    .dbApi(dbApi)
                    .webApi(webApi)
                    .build()
                    .also {
                        loginComponent = it
                    }
        }
    }

    fun inject(creatingAvatarFragment: CreatingAvatarFragment)
}