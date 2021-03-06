package com.bonusgaming.battleofmindskotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainViewModel @Inject constructor(mainModel: MainModel) : ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"
    }

    private var liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()
    private val disposable: Disposable

    init {
        Log.e("9789","-----------------MainViewModel init")
        disposable = mainModel.globalFragmentsState.subscribe {
            Log.e("9789","-----------------MainViewModel subscribe")
            liveFragmentState.value = it
        }
    }

    override fun onCleared() {
        Log.e("9789","----------onCleared")
        if (!disposable.isDisposed) disposable.dispose()
        Log.e("9977", "main onCleared }")
    }

    fun getData(): LiveData<FragmentState> {
        return liveFragmentState
    }

    fun onViewCreated() {

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