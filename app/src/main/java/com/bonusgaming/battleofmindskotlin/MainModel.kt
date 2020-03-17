package com.bonusgaming.battleofmindskotlin

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bonusgaming.battleofmindskotlin.tools.ACTION_CHANGE_FRAGMENT_STATE
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Singleton


@Singleton
class MainModel : MainContract.Model() {

    companion object {
        const val KEY_AVATAR_CREATED: String = "avatar_key"
    }

    fun setCurrentState(state: FragmentState) {
        emitter.onNext(state)
    }

    private val receiverFragmentState = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("FrView", "on receiverFragmentState")

            intent?.let {
                Log.e("FrView", "on intent not null")
                val fragmentState = intent.getSerializableExtra("FragmentState") as FragmentState
                setCurrentState(fragmentState)
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(appContext).apply {
            val intentFilter = IntentFilter(ACTION_CHANGE_FRAGMENT_STATE)
            registerReceiver(receiverFragmentState, intentFilter)
        }
    }

    @SuppressLint("ResourceType")
    fun getCardColor(): Int {
        return Color.parseColor(appContext.getString(R.color.colorCardBackground))
    }

    private lateinit var emitter: ObservableEmitter<FragmentState>


    val globalFragmentsState: Observable<FragmentState> =
            Observable.create { emitter = it }
}