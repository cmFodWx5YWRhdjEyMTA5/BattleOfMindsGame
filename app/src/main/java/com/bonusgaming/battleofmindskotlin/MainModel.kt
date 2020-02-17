package com.bonusgaming.battleofmindskotlin

import android.annotation.SuppressLint
import android.graphics.Color
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

const val KEY_AVATAR_CREATED: String = "avatar_key"

class MainModel : MainContract.Model() {

    fun setCurrentState(state: FragmentState) {
        emitter.onNext(state)
    }

    @SuppressLint("ResourceType")
    fun getCardColor(): Int {
        return Color.parseColor(appContext.getString(R.color.colorCardBackground))
    }

    private lateinit var emitter: ObservableEmitter<FragmentState>


    val globalFragmentsState: Observable<FragmentState> =
        Observable.create { emitter = it }

    override fun isAvatarCreated(): Boolean {
        // return prefs.getBoolean(KEY_AVATAR_CREATED) for debug
        return false
    }

    override fun setAvatarCreated() {
        prefs.putBoolean(KEY_AVATAR_CREATED, true)
    }

}