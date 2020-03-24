package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import android.content.Context
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppProvider
import com.bonusgaming.battleofmindskotlin.di.module.ApplicationModule
import com.bonusgaming.battleofmindskotlin.di.module.FragmentModule
import com.bonusgaming.battleofmindskotlin.di.module.ResourcesModule
import com.bonusgaming.battleofmindskotlin.di.module.ViewModelModule
import com.bonusgaming.battleofmindskotlin.features.login.di.component.LoginComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import java.security.Provider
import javax.inject.Singleton

@PerFacade
@Component(
        dependencies = [DbApi::class, WebApi::class, AppProvider::class],
        modules = [ViewModelModule::class, FragmentModule::class])
interface FacadeComponent
    : AppFacade, WebApi, DbApi {

    companion object {

        fun get(appProvider: AppProvider, dbApi: DbApi, webApi: WebApi): FacadeComponent {
            return DaggerFacadeComponent.builder().appFacade(appFacade)
                    .dbApi(dbApi)
                    .webApi(webApi)
                    .build()
                    .also {
                        loginComponent = it
                    }
        }
    }

}