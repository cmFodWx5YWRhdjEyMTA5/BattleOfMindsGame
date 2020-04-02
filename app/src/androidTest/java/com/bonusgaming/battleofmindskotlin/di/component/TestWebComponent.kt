package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.bonusgaming.battleofmindskotlin.di.module.WebTestModule
import dagger.Component

@PerFacade
@Component(modules = [WebTestModule::class])
interface TestWebComponent : WebApi{
    companion object {
        fun getWebComponent(): TestWebComponent = DaggerTestWebComponent.create()
    }
}