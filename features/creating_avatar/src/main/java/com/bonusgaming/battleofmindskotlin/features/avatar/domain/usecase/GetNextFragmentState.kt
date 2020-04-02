package com.bonusgaming.battleofmindskotlin.features.avatar.domain.usecase

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetNextFragmentState @Inject constructor() {
    fun execute(): FragmentState {
        return FragmentState.MENU
    }
}
