package com.bonusgaming.battleofmindskotlin.core.main.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface CoreComponent : CoreApi {

    companion object {
        private var appComponent: CoreComponent? = null

        fun get(context: Context): CoreComponent {
            return appComponent ?: DaggerCoreComponent.builder().application(context).build()
                    .also {
                        appComponent = it
                    }
        }
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        fun build(): CoreComponent
    }
}
