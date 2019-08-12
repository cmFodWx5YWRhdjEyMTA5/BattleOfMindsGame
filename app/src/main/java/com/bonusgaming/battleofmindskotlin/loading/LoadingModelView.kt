package com.bonusgaming.battleofmindskotlin.loading

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.MainContract
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
        disposable = Completable.complete().delay(Random.nextLong(0, 1), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveDataVisibleEnemy.value = View.VISIBLE
                liveDataVisibleLoading.value = false
                changeStateInModelDelay()
            }
    }

    private fun changeStateInModelDelay() {
        disposable = Completable.complete().delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("123231","changeStateInModelDelay")
                model.setCurrentState(FragmentState.GAME)
            }
    }
}