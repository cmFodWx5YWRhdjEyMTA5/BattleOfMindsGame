package com.bonusgaming.battleofmindskotlin.ui.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DELAY_SECOND = 3L

@PerFacade
class MainViewModel @Inject constructor(mainModel: MainModel) : ViewModel() {
    companion object {
        const val TAG: String = "MainViewModel"
    }

    private var liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()
    private val disposable: Disposable

    init {
        Log.e("9789", "-----------------MainViewModel init")
        disposable = mainModel.globalFragmentsState.subscribe {
            Log.e("9789", "-----------------MainViewModel subscribe")
            liveFragmentState.value = it
        }
    }

    override fun onCleared() {
        Log.e("9789", "----------onCleared")
        if (!disposable.isDisposed) disposable.dispose()
        Log.e("9977", "main onCleared }")
    }

    fun getData(): LiveData<FragmentState> {
        return liveFragmentState
    }

    fun onViewCreated() {

        liveFragmentState.value = FragmentState.LOGO

        Completable.complete()
                .delay(DELAY_SECOND, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    liveFragmentState.value = FragmentState.DOWNLOAD_ASSETS
                }
                .subscribe()
    }
}
