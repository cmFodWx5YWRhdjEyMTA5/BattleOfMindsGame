package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import javax.inject.Inject

class SaveStickerToDiskUseCase @Inject constructor(private val modelLoadingAssets: LoadingAssetsRepository) {
    fun execute(sticker: Sticker, bitmap: Bitmap) {
        modelLoadingAssets.saveBitmapToDisk(sticker, bitmap)
    }
}