package com.bonusgaming.battleofmindskotlin

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerFacadeComponent
import com.bonusgaming.battleofmindskotlin.di.component.TestWebComponent

class TestApp : App() {
    override fun onCreate() {
        super.onCreate()
        facadeComponent = DaggerFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbComponent.get(this))
                .webApi(TestWebComponent.getWebComponent()).build()
        println("facade in test ${facadeComponent.hashCode()}")
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