package com.bonusgaming.battleofmindskotlin.features.menu.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.menu.domain.model.ClickType
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetFragmentStateUseCase @Inject constructor() {
    fun execute(state: ClickType): FragmentState {
        return when (state) {
            ClickType.START -> FragmentState.LOADING
            ClickType.STATISTICS -> FragmentState.STATISTICS
        }
    }
}