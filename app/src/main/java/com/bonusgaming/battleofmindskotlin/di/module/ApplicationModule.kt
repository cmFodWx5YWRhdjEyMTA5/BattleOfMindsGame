package com.bonusgaming.battleofmindskotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ApplicationModule {

    @Binds
    internal abstract fun bindContext(application: Application): Context


}