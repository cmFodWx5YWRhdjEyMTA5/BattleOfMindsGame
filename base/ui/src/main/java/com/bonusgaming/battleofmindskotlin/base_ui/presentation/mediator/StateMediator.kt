package com.bonusgaming.battleofmindskotlin.base_ui.presentation.mediator

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState

interface StateMediator {
    fun nextState(state: FragmentState)
}