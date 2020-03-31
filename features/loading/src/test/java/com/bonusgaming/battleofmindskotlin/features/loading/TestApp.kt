package com.bonusgaming.battleofmindskotlin.features.loading

import android.app.Application
import android.util.Log
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.DaggerTestAppComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.DaggerTestFacadeComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.TestAppComponent
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.TestFacadeComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestApp : Application(), AppFacadeProvider, WebApiProvider, DbApiProvider {
    companion object {
        lateinit var facadeComponent: TestFacadeComponent
    }

    lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder().application(this).build()
        facadeComponent = DaggerTestFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbComponent.get(this))
                .webApi(WebComponent.getWebComponent()).build()
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
