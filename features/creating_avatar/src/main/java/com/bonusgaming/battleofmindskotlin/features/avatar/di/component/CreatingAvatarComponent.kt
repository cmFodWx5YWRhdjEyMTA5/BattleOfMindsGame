package com.bonusgaming.battleofmindskotlin.features.avatar.di.component

import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.webapi.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.features.avatar.di.module.CreatingAvatarViewModelModule
import dagger.Component

@PerFeature
@Component(dependencies = [UiComponent::class, AppFacade::class, DbApi::class, WebApi::class],
        modules = [CreatingAvatarViewModelModule::class])
interface CreatingAvatarComponent {
    companion object {
        fun get(appFacade: AppFacade, ui: UiComponent, web: WebApi, db: DbApi): CreatingAvatarComponent {
            return DaggerCreatingAvatarComponent.builder()
                    .appFacade(appFacade)
                    .uiComponent(ui)
                    .webApi(web)
                    .dbApi(db)
                    .build()
        }
    }

    fun inject(view: CreatingAvatarFragment)
}
