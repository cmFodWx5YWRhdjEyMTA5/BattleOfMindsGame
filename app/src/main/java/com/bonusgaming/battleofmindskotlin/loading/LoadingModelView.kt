package com.bonusgaming.battleofmindskotlin.loading

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.MainContract
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class LoadingModelView : MainContract.ViewModel() {
    override fun onViewCreated() {
    }

    val liveDataVisibleEnemy: MutableLiveData<Int> = MutableLiveData()
    val liveDataVisibleLoading: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var disposable: Disposable

    fun onCreated() {
        liveDataVisibleEnemy.value = View.GONE
        disposable = Completable.complete().delay(Random.nextLong(5, 9), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveDataVisibleEnemy.value = View.VISIBLE
                liveDataVisibleLoading.value = false
            }
    }

    fun onStoppedLoading() {
        model.setCurrentState(FragmentState.GAME)
    }

}