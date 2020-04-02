package com.bonusgaming.battleofmindskotlin

import android.util.Log
import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.dbapi.DbApiProvider
import com.bonusgaming.battleofmindskotlin.dbimpl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.webapi.WebApiProvider
import com.bonusgaming.battleofmindskotlin.webimpl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.di.component.AppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerFacadeComponent
import com.bonusgaming.battleofmindskotlin.di.component.FacadeComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


open class App : DaggerApplication(), AppFacadeProvider, WebApiProvider, DbApiProvider {
    companion object {
        lateinit var facadeComponent: FacadeComponent
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Log.e("App", "oncreate")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        facadeComponent = DaggerFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbComponent.get(this))
                .webApi(WebComponent.getWebComponent()).build()
        println("facade in app ${facadeComponent.hashCode()}")
        return appComponent
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
