package com.bonusgaming.battleofmindskotlin

import android.content.Context
import android.util.Log
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppProvider
import com.bonusgaming.battleofmindskotlin.di.component.AppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication(), AppFacadeProvider {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("App", "oncreate")
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this)
                .dbComponent(DbComponent.get(this))
                .webComponent(WebComponent.getWebComponent()).build()
        return appComponent
    }

    override fun provideAppFacade(): AppFacade {
        return appComponent
    }


}
