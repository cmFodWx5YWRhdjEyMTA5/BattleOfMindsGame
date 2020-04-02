package com.bonusgaming.battleofmindskotlin.webimpl.di.component

import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.webimpl.di.module.WebModule
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import dagger.Component

@PerFacade
@Component(modules = [WebModule::class])
abstract class WebComponent : WebApi {

    companion object {
        fun getWebComponent(): WebComponent = DaggerWebComponent.create()
    }
}
