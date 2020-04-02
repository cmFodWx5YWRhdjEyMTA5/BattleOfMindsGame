
package com.bonusgaming.battleofmindskotlin.core.main.contract

import com.bonusgaming.battleofmindskotlin.core.main.Prefs

interface PrefsFactory {
    fun providePrefs(): Prefs
}
