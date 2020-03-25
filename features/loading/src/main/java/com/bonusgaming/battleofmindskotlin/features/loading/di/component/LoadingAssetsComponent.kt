package com.bonusgaming.battleofmindskotlin.features.loading.di.component

import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.loading.LoadingAssetsFragment
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