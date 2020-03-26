package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetNextFragmentStateUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {
    fun execute(): FragmentState {
        return when (repository.isAvatarCreated()) {
            true -> FragmentState.MENU
            false -> FragmentState.AVATAR
        }
    }
}