package com.bonusgaming.battleofmindskotlin.loading_assets

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.MainContract
import com.bonusgaming.battleofmindskotlin.R
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


/*
Реализуем ViewModel для MVVM,
 */
class LoadingAssetsViewModel : MainContract.ViewModel() {

    @Inject
    lateinit var modelLoadingAssets: LoadingAssetsModel

    @Inject
    lateinit var resources: Resources

    private var currentProgress = 0f

    val textStatusLine2LiveData = MutableLiveData<String>()
    val loadSceneLiveData = MutableLiveData<FragmentState>()
    val textStatusLine1LiveData = MutableLiveData<String>()
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
                textStatusLine1LiveData.value =
                    resources.getString(R.string.desire_emotion_bad_connection_status)
                textStatusLine2LiveData.value =
                    resources.getString(R.string.desire_emotion_bad_connection_action)
            }
            .doOnSuccess {
                textStatusLine1LiveData.value = ""
                textStatusLine2LiveData.value = resources.getString(R.string.download)
                proceedResult(it)
            }
            .retryWhen { t -> t.delay(5, TimeUnit.SECONDS) }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun nextScene(){

    }

    private fun proceedResult(list: List<Item>) {
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
            if (progressRounded == 100) {
                textStatusLine1LiveData.value = ""
                textStatusLine2LiveData.value = resources.getString(R.string.download_complete)
            } else {
                textStatusLine1LiveData.value = ""
                textStatusLine2LiveData.value =
                    resources.getString(R.string.download) + " $progressRounded%"
            }
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
                val onException: (url: String) -> Unit = {
                    Log.e("retry", "retry download")
                    textStatusLine1LiveData.value =
                        resources.getString(R.string.desire_emotion_bad_connection_status)
                    textStatusLine2LiveData.value =
                        resources.getString(R.string.desire_emotion_bad_connection_action)
                    modelLoadingAssets.retryDownload(item.mediaLink, 5000)
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








