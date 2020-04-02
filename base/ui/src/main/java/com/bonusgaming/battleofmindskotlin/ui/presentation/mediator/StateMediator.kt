package com.bonusgaming.battleofmindskotlin.ui.presentation.mediator

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState

interface StateMediator {
    fun nextState(state: FragmentState)
}
