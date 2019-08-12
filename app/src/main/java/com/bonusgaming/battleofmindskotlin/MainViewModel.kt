package com.bonusgaming.battleofmindskotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

const val TAG: String = "MenuViewModel"

class MainViewModel : MainContract.ViewModel() {
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
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                if (model.isAvatarCreated()) {
                    Log.e(TAG, "viewcreated MAIN")
                    liveFragmentState.value = FragmentState.MAIN
                } else {
                    Log.e(TAG, "viewcreated AVATAR")
                    liveFragmentState.value = FragmentState.AVATAR
                }
            }
            .subscribe()
    }
}