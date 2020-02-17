package com.bonusgaming.battleofmindskotlin.loading_assets

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainContract
import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.Database
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.bonusgaming.battleofmindskotlin.web.Item
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.round

class LoadingAssetsViewModel : MainContract.ViewModel() {

    @Inject
    lateinit var modelLoadingAssets: ModelLoadingAssets

    @Inject
    lateinit var database: Database

    @Inject
    lateinit var pathProvider: PathProvider

    private var currentProgress = 0f

    //string reference for Image target
    private val listImageTarget = mutableListOf<ImageTarget>()

    val textStatusLiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Int>()

    private lateinit var disposable: Disposable

    init {
        App.appComponent.inject(this)
    }

    override fun onViewCreated() {

        disposable = modelLoadingAssets.getFaceUrls()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    proceedResult(it)
                },
                {
                    Log.e("webtest", " fail $it")
                })
    }

    private fun proceedResult(list: List<Item>) {
        //TODO check inet connection
        val perProgress = 100F / list.size

        viewModelScope.launch(Dispatchers.IO) {

        }

        val currentStickerList = database.stickersDao().getHashStickersList()


        for (item in list) {

            if (item.md5Hash in currentStickerList) continue

            val name = item.name.replace('/', '_')
            val imageTarget = ImageTarget(name) {
                currentProgress += perProgress
                Log.e("CHeckD", "progress viewmodel $currentProgress")
                val progressRounded = round(currentProgress).toInt()
                progressLiveData.value = progressRounded
                if (progressRounded == 100)
                    textStatusLiveData.value = "Загрузка завершена"
                else
                    textStatusLiveData.value = "Загрузка $progressRounded%"

                val pathItem = pathProvider.getImagesPath() + name
                val sticker = StickerEntry(item.md5Hash, pathItem)
                database.stickersDao().insertAll(sticker)
            }
            listImageTarget.add(imageTarget)
            Picasso.get().load(item.mediaLink)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(imageTarget)
        }
    }

    override fun onCleared() {
        super.onCleared()
        listImageTarget.clear()
        disposable.dispose()
    }
}








