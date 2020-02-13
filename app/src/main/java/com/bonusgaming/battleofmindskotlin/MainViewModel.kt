package com.bonusgaming.battleofmindskotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class MainViewModel : MainContract.ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"

    }

    private var liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()
    private val disposable: Disposable

    init {
        disposable = model.globalFragmentsState.subscribe {
            liveFragmentState.value = it
        }
    }

    override fun onCleared() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    fun getData(): LiveData<FragmentState> {
        return liveFragmentState
    }

    override fun onViewCreated() {
        Log.e(TAG, "viewcreated $model")

        liveFragmentState.value = FragmentState.LOGO

        Completable.complete()
            .delay(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                liveFragmentState.value = FragmentState.DOWNLOAD_ASSETS
            }
            .subscribe()
    }
}