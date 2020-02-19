package com.bonusgaming.battleofmindskotlin.loading_assets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainContract
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.web.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

class LoadingAssetsViewModel : MainContract.ViewModel() {

    @Inject
    lateinit var modelLoadingAssets: LoadingAssetsModel


    private var currentProgress = 0f

    val textStatusLiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Int>()

    private val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent.inject(this)
    }

    override fun onViewCreated() {
        startDownloadUrls()
    }

    private fun startDownloadUrls() {
        val disposable = modelLoadingAssets.getFaceUrls()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                textStatusLiveData.value = "Пытаемся соединиться..."
            }
            .doOnSuccess {
                textStatusLiveData.value = "Загрузка"
                proceedResult(it)
            }
            .retryWhen { t -> t.delay(5, TimeUnit.SECONDS) }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun proceedResult(list: List<Item>) {
        //TODO check inet connection
        val perProgress = 100F / list.size
        fun saveToDb(sticker: StickerEntry) {
            viewModelScope.launch(Dispatchers.IO) {
                modelLoadingAssets.addStickerToDb(sticker)
            }
        }

        fun updateProgress() {
            currentProgress += perProgress
            val progressRounded = round(currentProgress).toInt()
            progressLiveData.value = progressRounded
            if (progressRounded == 100)
                textStatusLiveData.value = "Загрузка завершена"
            else
                textStatusLiveData.value = "Загрузка $progressRounded%"
        }

        fun checkItem(databaseHashList: List<String>) {
            for (item in list) {
                if (item.md5Hash in databaseHashList) {
                    updateProgress()
                    continue
                }
                val name = item.name.replace('/', '_')
                val onDownload = {
                    updateProgress()
                    val sticker = StickerEntry(item.md5Hash, name)
                    saveToDb(sticker)
                }
                val onException = {

                }

                modelLoadingAssets.downloadAndSaveImage(
                    item.mediaLink,
                    name,
                    onDownload,
                    onException
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val databaseHashList = modelLoadingAssets.getHashStickersList()
            withContext(Dispatchers.Main) {
                checkItem(databaseHashList)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        modelLoadingAssets.listImageTarget.clear()
        compositeDisposable.dispose()
    }
}








