package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.component.DbComponent
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_impl.di.component.WebComponent
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppProvider
import com.bonusgaming.battleofmindskotlin.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Component(
        dependencies = [DbApi::class, WebApi::class],
        modules = [ViewModelModule::class, ApplicationModule::class, AndroidInjectionModule::class, ResourcesModule::class, FragmentModule::class])
interface AppComponent
    : AndroidInjector<App>, AppFacade {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun dbComponent(dbAPi: DbApi): Builder
        fun webComponent(webApi: WebApi): Builder
        fun build(): AppComponent
    }
}