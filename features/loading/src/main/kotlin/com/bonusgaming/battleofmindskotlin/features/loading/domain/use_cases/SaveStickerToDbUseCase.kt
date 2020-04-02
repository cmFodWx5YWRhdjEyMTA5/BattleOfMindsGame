package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SaveStickerToDbUseCase @Inject constructor(private val modelLoadingAssets: LoadingAssetsRepository) {
    fun execute(sticker: Sticker) {
        modelLoadingAssets.addStickerToDb(sticker)
    }
}