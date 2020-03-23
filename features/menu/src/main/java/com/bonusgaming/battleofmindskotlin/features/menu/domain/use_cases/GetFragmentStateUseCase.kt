package com.bonusgaming.battleofmindskotlin.main.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.main.domain.model.ClickType
import javax.inject.Inject


class GetFragmentStateUseCase @Inject constructor() {
    fun execute(state: ClickType): FragmentState {
        return when (state) {
            ClickType.START -> FragmentState.LOADING
            ClickType.STATISTICS -> FragmentState.STATISTICS
        }
    }
}