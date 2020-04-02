package com.bonusgaming.battleofmindskotlin

import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.dbimpl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.di.component.DaggerFacadeComponent
import com.bonusgaming.battleofmindskotlin.di.component.TestWebComponent

class TestApp : App() {
    override fun onCreate() {
        super.onCreate()
        facadeComponent = DaggerFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbComponent.get(this))
                .webApi(TestWebComponent.getWebComponent()).build()
    }

    override fun provideAppFacade(): AppFacade {
        return facadeComponent
    }

    override fun provideWebApi(): WebApi {
        return facadeComponent
    }

    override fun provideDbApi(): DbApi {
        return facadeComponent
    }
}