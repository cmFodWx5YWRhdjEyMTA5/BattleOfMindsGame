package com.bonusgaming.battleofmindskotlin.features.loading.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppProvider
import com.bonusgaming.battleofmindskotlin.features.loading.TestApp
import com.bonusgaming.battleofmindskotlin.features.loading.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [ApplicationModule::class])
interface TestAppComponent
    : AppProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }
}