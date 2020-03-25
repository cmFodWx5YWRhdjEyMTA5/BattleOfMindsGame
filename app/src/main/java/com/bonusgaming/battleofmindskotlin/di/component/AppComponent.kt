package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppProvider
import com.bonusgaming.battleofmindskotlin.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [ApplicationModule::class, AndroidInjectionModule::class])
interface AppComponent
    : AndroidInjector<App>, AppProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}