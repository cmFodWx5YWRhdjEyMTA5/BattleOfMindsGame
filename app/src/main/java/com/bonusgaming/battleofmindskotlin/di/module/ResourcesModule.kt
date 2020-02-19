package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ResourcesModule {
    @Provides
    fun getResources(context: Context) = context.resources
}