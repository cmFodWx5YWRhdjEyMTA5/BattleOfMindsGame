package com.bonusgaming.battleofmindskotlin.base_ui.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourcesModule {

    @Provides
    fun getResources(context: Context): Resources {
        return context.resources
    }
}