package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DELAY_SECONDS=5L

@Reusable
class DownloadUrlsUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {

    var isRetryWhenEnabled = true

    fun execute(doOnSuccess: (list: List<UrlSticker>) -> Unit, doOnError: (error: Throwable) -> Unit): Disposable {
        return repository.getStickerUrls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    doOnError(it) }
                .doOnSuccess {
                    doOnSuccess(it)
                }
                .retryWhen { t ->
                    t.filter { isRetryWhenEnabled }.delay(DELAY_SECONDS, TimeUnit.SECONDS)
                }
                .subscribe()
    }
}
