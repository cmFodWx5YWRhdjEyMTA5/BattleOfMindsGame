package com.bonusgaming.battleofmindskotlin.di.component

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppProvider
import com.bonusgaming.battleofmindskotlin.di.module.FragmentBinding
import com.bonusgaming.battleofmindskotlin.di.module.FragmentModule
import dagger.Component

@PerFacade
@Component(
        dependencies = [DbApi::class, WebApi::class, AppProvider::class],
        modules = [FragmentModule::class,FragmentBinding::class])
interface FacadeComponent
    : AppFacade, WebApi, DbApi {

    companion object {

        fun get(appProvider: AppProvider, dbApi: DbApi, webApi: WebApi): FacadeComponent {
            return DaggerFacadeComponent.builder().appProvider(appProvider)
                    .dbApi(dbApi)
                    .webApi(webApi)
                    .build()

        }
    }

}