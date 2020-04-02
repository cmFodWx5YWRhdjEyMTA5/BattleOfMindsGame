package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SaveStickerUseCase @Inject constructor(private val modelLoadingAssets: LoadingAssetsRepository) {
    fun saveToDb(sticker: Sticker) {
        modelLoadingAssets.addStickerToDb(sticker)
    }

    fun saveToDisk(sticker: Sticker, bitmap: Bitmap) {
        modelLoadingAssets.saveBitmapToDisk(sticker, bitmap)
    }
}
