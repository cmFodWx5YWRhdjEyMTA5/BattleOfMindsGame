package com.bonusgaming.battleofmindskotlin.features.loading.di.component

import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.di.module.LoadingAssetsViewModelModule
import dagger.Component

@PerFeature
@Component(dependencies = [AppFacade::class, UiComponent::class, WebApi::class, DbApi::class],
        modules = [LoadingAssetsViewModelModule::class])
interface LoadingAssetsComponent {
    companion object {
        fun get(appFacade: AppFacade, ui: UiComponent, web: WebApi, db: DbApi): LoadingAssetsComponent {
            return DaggerLoadingAssetsComponent.builder()
                    .appFacade(appFacade)
                    .uiComponent(ui)
                    .webApi(web)
                    .dbApi(db)
                    .build()

        }
    }

    fun inject(view: LoadingAssetsFragment)
}
