package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetSavedStickersListUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {
    fun execute(): List<String> {
        return repository.getHashStickersList()
    }
}
