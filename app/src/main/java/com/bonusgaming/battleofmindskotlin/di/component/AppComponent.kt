package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainContract
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.di.module.*
import com.bonusgaming.battleofmindskotlin.loading_assets.ImageTarget
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsViewModel
import com.bonusgaming.battleofmindskotlin.web.WebRepo
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ResourcesModule::class, PicassoModule::class, RetrofitModule::class, DatabaseModule::class, PrefsModule::class, ApplicationModule::class, AndroidInjectionModule::class, MainModelModule::class])
interface AppComponent : AndroidInjector<App> {

    fun inject(imageTarget: ImageTarget)
    fun inject(pathProvider: PathProvider)
    fun inject(model: MainContract.ViewModel)
    fun inject(model: MainContract.Model)
    fun inject(webRepo: WebRepo)
    fun inject(viewModel: LoadingAssetsViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}