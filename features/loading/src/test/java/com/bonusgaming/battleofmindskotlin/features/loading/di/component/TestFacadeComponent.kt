package com.bonusgaming.battleofmindskotlin.features.loading.di.component

import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppProvider
import com.bonusgaming.battleofmindskotlin.features.loading.di.module.FragmentBinding
import com.bonusgaming.battleofmindskotlin.features.loading.di.module.FragmentModule
import dagger.Component

@PerFacade
@Component(
        dependencies = [DbApi::class, WebApi::class, AppProvider::class],
        modules = [FragmentModule::class, FragmentBinding::class])
interface TestFacadeComponent
    : AppFacade, WebApi, DbApi {

    companion object {

        fun get(appProvider: AppProvider, dbApi: DbApi, webApi: WebApi): TestFacadeComponent {
            return DaggerTestFacadeComponent.builder().appProvider(appProvider)
                    .dbApi(dbApi)
                    .webApi(webApi)
                    .build()

        }
    }

}