package com.bonusgaming.battleofmindskotlin.loading_assets

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.*
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.data.LoadingAssetsRepository
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

@PerFragment
class LoadingAssetsViewModel @Inject constructor(private val modelLoadingAssets: LoadingAssetsRepository,
                                                 val resources: Resources) : ViewModel() {

    private var currentProgress = 0f
    private val compositeDisposable = CompositeDisposable()

    private val _textStatusLine2LiveData = MutableLiveData<String>()
    val textStatusLine2LiveData: LiveData<String> get() = _textStatusLine2LiveData

    private val _loadSceneLiveData = MutableLiveData<FragmentState>()
    val loadSceneLiveData: LiveData<FragmentState> get() = _loadSceneLiveData

    private val _textStatusLine1LiveData = MutableLiveData<String>()
    val textStatusLine1LiveData: LiveData<String> get() = _textStatusLine1LiveData


    val progressLiveData = MutableLiveData<Int>()

    init {
        Log.e("9977","init LoadingAsstestVM")
    }

    fun onViewCreated() {
        startDownloadUrls()
    }

    private fun nextFragmentState() {
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(2000)
            withContext(Dispatchers.Main) {
                _loadSceneLiveData.value = getNextFragmentState()
            }
        }
    }

    private fun startDownloadUrls() {
        val disposable = modelLoadingAssets.getFaceUrls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.e("startDownloadUrls", "error ${it.printStackTrace()}")
                    _textStatusLine1LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_status)
                    _textStatusLine2LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_action)
                    throw it
                }
                .doOnSuccess {
                    _textStatusLine1LiveData.value = ""
                    _textStatusLine2LiveData.value = resources.getString(R.string.download)
                    proceedResult(it)
                }
                .retryWhen { t -> t.delay(5, TimeUnit.SECONDS) }
                .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun proceedResult(list: List<Item>) {
        val perProgress = 100F / list.size
        fun saveSticker(sticker: StickerEntry, bitmap: Bitmap) {
            viewModelScope.launch(Dispatchers.IO) {
                modelLoadingAssets.addStickerToDb(sticker)
                modelLoadingAssets.saveBitmapToDisk(sticker.path, bitmap)
            }
        }


        fun updateProgress() {
            currentProgress += perProgress
            val progressRounded = round(currentProgress).toInt()
            progressLiveData.value = progressRounded
            if (progressRounded == 100) {
                _textStatusLine1LiveData.value = ""
                _textStatusLine2LiveData.value = resources.getString(R.string.download_complete)
                nextFragmentState()
            } else {
                _textStatusLine1LiveData.value = ""
                _textStatusLine2LiveData.value =
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
                val onDownload: (fileName: String, bitmap: Bitmap) -> Unit = { fileName, bitmap ->
                    val sticker = StickerEntry(item.md5Hash, fileName)
                    saveSticker(sticker, bitmap)
                    updateProgress()
                }
                val onException: (url: String) -> Unit = {
                    Log.e("retry", "retry download")
                    _textStatusLine1LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_status)
                    _textStatusLine2LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_action)
                    modelLoadingAssets.retryDownload(item.mediaLink, 5000)
                }
                modelLoadingAssets.downloadBitmapToDisk(
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

        Log.e("9977", "loading assets onCleared }")

    }

    private fun getNextFragmentState() = when (modelLoadingAssets.isAvatarCreated()) {
        true -> FragmentState.MAIN
        false -> FragmentState.AVATAR
    }
}








