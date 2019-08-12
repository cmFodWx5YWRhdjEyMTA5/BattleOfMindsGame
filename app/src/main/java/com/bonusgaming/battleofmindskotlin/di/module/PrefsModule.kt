package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import com.bonusgaming.battleofmindskotlin.Prefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Singleton
    @Provides
    fun providePrefs(context: Context): Prefs {
        return Prefs(context)
    }
}