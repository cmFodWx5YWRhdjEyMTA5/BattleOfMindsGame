package com.bonusgaming.battleofmindskotlin

import android.util.Log
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.di.component.AppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerFacadeComponent
import com.bonusgaming.battleofmindskotlin.di.component.FacadeComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


open class App : DaggerApplication(), AppFacadeProvider, WebApiProvider, DbApiProvider {

    private lateinit var facadeComponent: FacadeComponent


    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Log.e("App", "oncreate")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        facadeComponent = DaggerFacadeComponent.builder().appProvider(appComponent)
                .dbApi(DbComponent.get(this))
                .webApi(WebComponent.getWebComponent()).build()
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