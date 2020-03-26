package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import dagger.Reusable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class DownloadStickerUseCase @Inject constructor(private val repository: LoadingAssetsRepository) {

}