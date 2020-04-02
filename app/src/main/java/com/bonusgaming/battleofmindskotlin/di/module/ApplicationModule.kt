package com.bonusgaming.battleofmindskotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class ApplicationModule {

    @Singleton
    @Binds
    internal abstract fun bindContext(application: Application): Context
}
