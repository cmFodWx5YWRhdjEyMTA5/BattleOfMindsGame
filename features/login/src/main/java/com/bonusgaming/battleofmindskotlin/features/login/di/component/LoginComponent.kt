package com.bonusgaming.battleofmindskotlin.features.login.di.component

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.features.login.creating_avatar.CreatingAvatarFragment
import dagger.Component

@Component(dependencies = [AppFacade::class, DbApi::class, WebApi::class])
interface LoginComponent {
    companion object {
        private var loginComponent: LoginComponent? = null

        fun get(appFacade: AppFacade): LoginComponent {
            return loginComponent ?: DaggerLoginComponent.builder().appFacade(appFacade)
                    .dbApi()
                    .build()
                    .also {
                        loginComponent = it
                    }
        }
    }

    fun inject(creatingAvatarFragment: CreatingAvatarFragment)
}