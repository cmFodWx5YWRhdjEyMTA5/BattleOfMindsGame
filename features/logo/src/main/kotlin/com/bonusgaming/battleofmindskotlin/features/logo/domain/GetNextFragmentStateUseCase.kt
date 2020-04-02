package com.bonusgaming.battleofmindskotlin.features.logo.domain

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import dagger.Reusable
import kotlinx.coroutines.delay
import javax.inject.Inject

@Reusable
class GetNextFragmentStateUseCase @Inject constructor() {
    suspend fun execute(afterMilliSecconds: Long): FragmentState {
        delay(afterMilliSecconds)
        return FragmentState.LOADING
    }
}