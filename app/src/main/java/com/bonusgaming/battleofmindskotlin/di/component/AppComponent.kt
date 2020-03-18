package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainActivityView
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.di.module.*
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.domain.model.ImageTarget
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.main.ui.MenuFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MainActivityViewModelModule::class, ResourcesModule::class, WebModule::class, DatabaseModule::class, ApplicationModule::class, AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(activity: MainActivityView)

    fun getCreatingAvatarComponent(): CreatingAvatarComponent
    fun getMenuComponent(): MenuComponent
    fun getLoadingAssetsComponent(): LoadingAssetsComponent
    fun getLoadingComponent(): LoadingComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}