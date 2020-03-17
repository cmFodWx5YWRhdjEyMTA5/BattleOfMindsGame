package com.bonusgaming.battleofmindskotlin.loading_assets

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.*
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.tools.ACTION_CHANGE_FRAGMENT_STATE
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
    val loadSceneLiveData = MutableLiveData<Intent>()
    val textStatusLine1LiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Int>()

    private val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent.inject(this)
    }

    override fun onViewCreated() {
        startDownloadUrls()
    }

    private fun nextFragmentState() {
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(2000)
            withContext(Dispatchers.Main) {
                loadSceneLiveData.value = getNextFragmentIntent()
            }
        }
    }

    private fun startDownloadUrls() {
        val disposable = modelLoadingAssets.getFaceUrls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.e("startDownloadUrls", "error ${it.printStackTrace()}")
                    textStatusLine1LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_status)
                    textStatusLine2LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_action)
                    throw it
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
                nextFragmentState()
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
                    val sticker = StickerEntry(item.md5Hash, name)
                    saveToDb(sticker)
                    updateProgress()
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

    private fun getNextFragmentIntent() = Intent(ACTION_CHANGE_FRAGMENT_STATE).also {
        when (modelLoadingAssets.isAvatarCreated()) {
            true -> it.putExtra("FragmentState", FragmentState.MAIN)
            false -> it.putExtra("FragmentState", FragmentState.AVATAR)
        }
    }
}








