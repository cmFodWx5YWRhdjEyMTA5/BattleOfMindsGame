package com.bonusgaming.battleofmindskotlin.base_web_impl.di.component

import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.module.WebModule
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import dagger.Component
import javax.inject.Singleton

@PerFacade
@Component(modules = [WebModule::class])
abstract class WebComponent : WebApi {

    companion object {
        fun getWebComponent(): WebComponent = DaggerWebComponent.create()
    }
}