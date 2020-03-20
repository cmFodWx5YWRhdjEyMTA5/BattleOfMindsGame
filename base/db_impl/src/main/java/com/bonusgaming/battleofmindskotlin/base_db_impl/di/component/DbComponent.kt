package com.bonusgaming.battleofmindskotlin.base_db_impl.di.component

import android.app.Application
import android.content.Context
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_impl.di.module.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
abstract class DbComponent : DbApi {
    companion object {

        private var appComponent: DbComponent? = null

        fun get(): DbComponent {
            return appComponent ?: DaggerDbComponent.create()
                    .also {
                        appComponent = it
                    }
        }
    }

}

