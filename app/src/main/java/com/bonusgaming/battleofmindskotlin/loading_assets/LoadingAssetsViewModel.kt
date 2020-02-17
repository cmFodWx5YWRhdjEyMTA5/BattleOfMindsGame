package com.bonusgaming.battleofmindskotlin.loading_assets

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainContract
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.round

class LoadingAssetsViewModel : MainContract.ViewModel() {

    @Inject
    lateinit var modelLoadingAssets: ModelLoadingAssets

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

    private fun proceedResult(map: Map<String, String>) {
        //TODO check in database, if not - download
        //TODO check inet connection
        val perProgress = 100F / map.size
        for ((url, name) in map) {
            val imageTarget = ImageTarget(name) {
                currentProgress += perProgress
                Log.e("CHeckD", "progress viewmodel $currentProgress")
                val progressRounded = round(currentProgress).toInt()
                progressLiveData.value = progressRounded
                if (progressRounded == 100)
                    textStatusLiveData.value = "Загрузка завершена"
                else
                    textStatusLiveData.value = "Загрузка $progressRounded%"
            }
            listImageTarget.add(imageTarget)
            Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(imageTarget)
        }
    }

    override fun onCleared() {
        super.onCleared()
        listImageTarget.clear()
        disposable.dispose()
    }
}








