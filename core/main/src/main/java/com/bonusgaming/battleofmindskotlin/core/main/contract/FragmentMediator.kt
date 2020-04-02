package com.bonusgaming.battleofmindskotlin.core.main.contract

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState

interface FragmentMediator {
    fun provideFragments(): Map< FragmentState, Fragment>
}
