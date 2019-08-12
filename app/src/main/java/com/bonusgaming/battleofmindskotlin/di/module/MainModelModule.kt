package com.bonusgaming.battleofmindskotlin.di.module

import com.bonusgaming.battleofmindskotlin.MainModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModelModule {
    @Singleton
    @Provides
    fun provideModel(): MainModel {
        return MainModel()
    }
}