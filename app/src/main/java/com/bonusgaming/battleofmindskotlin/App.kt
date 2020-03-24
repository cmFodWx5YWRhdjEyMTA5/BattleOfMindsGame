package com.bonusgaming.battleofmindskotlin

import android.util.Log
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.di.component.AppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerFacadeComponent
import com.bonusgaming.battleofmindskotlin.di.component.FacadeComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication(), AppFacadeProvider, WebApiProvider, DbApiProvider {
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
//        facadeComponent=DaggFa
//        facadeComponent = DaggerFacadeComponent.builder(). DaggerAppComponent.builder().application(this)
//                .dbComponent(DbComponent.get(this))
//                .webComponent(WebComponent.getWebComponent()).build()
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
