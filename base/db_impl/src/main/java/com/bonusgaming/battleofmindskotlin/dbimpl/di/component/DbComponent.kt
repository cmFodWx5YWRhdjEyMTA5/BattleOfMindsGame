package com.bonusgaming.battleofmindskotlin.dbimpl.di.component

import android.content.Context
import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.dbimpl.di.module.DatabaseModule
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import dagger.BindsInstance
import dagger.Component

@PerFacade
@Component(modules = [DatabaseModule::class])
abstract class DbComponent : DbApi {
    companion object {

        private var appComponent: DbComponent? = null

        fun get(context: Context): DbComponent {
            return appComponent ?: DaggerDbComponent.builder().application(context).build()
                    .also {
                        appComponent = it
                    }
        }
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder
        fun build(): DbComponent
    }
}

