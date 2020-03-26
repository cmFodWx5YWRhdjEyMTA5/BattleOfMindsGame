package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class DownloadUrlsUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {
    fun execute(doOnSuccess: (list: List<UrlSticker>) -> Unit, doOnError: (error: Throwable) -> Unit): Disposable {
        return repository.getFaceUrls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { doOnError(it) }
                .doOnSuccess { doOnSuccess(it) }
                .retryWhen { t -> t.delay(5, TimeUnit.SECONDS) }
                .subscribe()
    }
}