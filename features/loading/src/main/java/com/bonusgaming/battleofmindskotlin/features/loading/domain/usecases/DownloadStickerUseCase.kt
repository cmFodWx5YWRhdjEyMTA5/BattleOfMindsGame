package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import javax.inject.Inject

private const val DELAY = 5000L

@Reusable
class DownloadStickerUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {
    fun download(urlSticker: UrlSticker,
                 onDownload: (bitmap: Bitmap) -> Unit,
                 onException: (url: String) -> Unit) {
        repository.downloadUrlStickerToDisk(
                urlSticker,
                onDownload,
                onException
        )
    }

    fun retryDownloadWithDelay(urlSticker: UrlSticker) {
        repository.retryDownload(urlSticker, DELAY)
    }
}
