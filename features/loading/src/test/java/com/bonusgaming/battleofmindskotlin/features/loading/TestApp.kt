package com.bonusgaming.battleofmindskotlin.features.loading

import android.app.Application
import android.util.Log
import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.dbapi.DbApiProvider
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.webapi.WebApiProvider
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.DaggerTestAppComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.DaggerTestFacadeComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.TestAppComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.TestFacadeComponent


class TestApp : Application(), AppFacadeProvider, WebApiProvider, DbApiProvider {
    companion object {
        lateinit var facadeComponent: TestFacadeComponent
    }

    lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder().application(this).build()
        facadeComponent = DaggerTestFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbStub())
                .webApi(WebStub()).build()
        Log.e("App", "oncreate")
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
