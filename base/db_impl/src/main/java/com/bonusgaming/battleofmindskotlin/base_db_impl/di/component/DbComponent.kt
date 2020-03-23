package com.bonusgaming.battleofmindskotlin.base_db_impl.di.component

import android.content.Context
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.module.DatabaseModule
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@PerFeature
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

