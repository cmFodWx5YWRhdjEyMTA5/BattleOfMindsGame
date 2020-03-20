
package com.bonusgaming.battleofmindskotlin.core.main.mediator

import android.content.Context
import com.bonusgaming.battleofmindskotlin.core.main.Prefs

interface PrefsFactory {
    fun providePrefs(): Prefs
}