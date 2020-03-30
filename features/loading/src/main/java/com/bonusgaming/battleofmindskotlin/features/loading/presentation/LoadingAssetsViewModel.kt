package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.round

@PerFeature
class LoadingAssetsViewModel
@Inject constructor
(private val downloadUrlsUseCase: DownloadUrlsUseCase,
 private val downloadStickerUseCase: DownloadStickerUseCase,
 private val getNextFragmentStateUseCase: GetNextFragmentStateUseCase,
 private val saveStickerToDbUseCase: SaveStickerToDbUseCase,
 private val saveStickerToDiskUseCase: SaveStickerToDiskUseCase,
 private val getSavedStickersList: GetSavedStickersListUseCase,
 private val resources: Resources) : ViewModel() {

    var isAllDisposablesClosed = false
        get() = compositeDisposable.isDisposed
        private set

    private var currentProgress = 0f
    private val compositeDisposable = CompositeDisposable()

    private val _textStatusLine2LiveData = MutableLiveData<String>()
    val textStatusLine2LiveData: LiveData<String> get() = _textStatusLine2LiveData

    private val _loadSceneLiveData = MutableLiveData<FragmentState>()
    val loadSceneLiveData: LiveData<FragmentState> get() = _loadSceneLiveData

    private val _textStatusLine1LiveData = MutableLiveData<String>()
    val textStatusLine1LiveData: LiveData<String> get() = _textStatusLine1LiveData

    private val _progressLiveData = MutableLiveData<Int>()
    val progressLiveData: LiveData<Int> get() = _progressLiveData

    init {
        Log.e("9977", "init LoadingAsstestVM")
    }

    fun onViewCreated() {
        startDownloadUrls()
    }

    private fun nextFragmentState() {
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(2000)
            withContext(Dispatchers.Main) {
                _loadSceneLiveData.value = getNextFragmentStateUseCase.execute()
            }
        }
    }

    private fun startDownloadUrls() {
        val disposable = downloadUrlsUseCase.execute({
            _textStatusLine1LiveData.value = ""
            _textStatusLine2LiveData.value = resources.getString(R.string.download)
            proceedResult(it)
        }, {
            _textStatusLine1LiveData.value =
                    resources.getString(R.string.desire_emotion_bad_connection_status)
            _textStatusLine2LiveData.value =
                    resources.getString(R.string.desire_emotion_bad_connection_action)
            throw it
        })
        compositeDisposable.add(disposable)
    }

    private fun proceedResult(list: List<UrlSticker>) {
        val perProgress = 100F / list.size
        fun saveSticker(sticker: Sticker, bitmap: Bitmap) {
            viewModelScope.launch(Dispatchers.IO) {
                saveStickerToDbUseCase.execute(sticker)
                saveStickerToDiskUseCase.execute(sticker, bitmap)
            }
        }
        fun updateProgress() {
            currentProgress += perProgress
            val progressRounded = round(currentProgress).toInt()
            _progressLiveData.value = progressRounded
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
            for (urlSticker in list) {
                if (urlSticker.md5Hash in databaseHashList) {
                    updateProgress()
                    continue
                }
                val name = urlSticker.name.replace('/', '_')
                val namedUrlSticker = UrlSticker(urlSticker.mediaLink, name, urlSticker.size, urlSticker.md5Hash)

                val onDownload: (bitmap: Bitmap) -> Unit = { bitmap ->
                    val sticker = Sticker(namedUrlSticker.md5Hash, namedUrlSticker.name)
                    saveSticker(sticker, bitmap)
                    updateProgress()
                }
                val onException: (url: String) -> Unit = {
                    Log.e("retry", "retry download")
                    _textStatusLine1LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_status)
                    _textStatusLine2LiveData.value =
                            resources.getString(R.string.desire_emotion_bad_connection_action)
                    downloadStickerUseCase.retryDownloadWithDelay(namedUrlSticker)
                }
                downloadStickerUseCase.download(
                        namedUrlSticker,
                        onDownload,
                        onException
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val databaseHashList = getSavedStickersList.execute()
            withContext(Dispatchers.Main) {
                checkItem(databaseHashList)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        Log.e("9977", "loading assets onCleared }")
    }
}








