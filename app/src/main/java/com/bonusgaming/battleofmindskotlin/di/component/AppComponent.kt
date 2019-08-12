package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainContract
import com.bonusgaming.battleofmindskotlin.di.module.ApplicationModule
import com.bonusgaming.battleofmindskotlin.di.module.DatabaseModule
import com.bonusgaming.battleofmindskotlin.di.module.MainModelModule
import com.bonusgaming.battleofmindskotlin.di.module.PrefsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, PrefsModule::class, ApplicationModule::class, AndroidInjectionModule::class, MainModelModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(model: MainContract.ViewModel)
    fun inject(model: MainContract.Model)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}