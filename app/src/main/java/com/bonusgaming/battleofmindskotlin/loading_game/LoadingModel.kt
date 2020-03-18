package com.bonusgaming.battleofmindskotlin.loading_game

import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import javax.inject.Inject

@PerFragment
class LoadingModel @Inject constructor() {
    fun setCurrentState(fragmentState: FragmentState) {
    }
}