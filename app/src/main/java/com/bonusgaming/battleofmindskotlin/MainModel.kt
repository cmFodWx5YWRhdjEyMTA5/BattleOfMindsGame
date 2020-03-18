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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainModel @Inject constructor(private val appContext: Context) {

    private lateinit var emitter: ObservableEmitter<FragmentState>

    val globalFragmentsState: Observable<FragmentState> = Observable.create { emitter = it }

    init{
        Log.e("9789", " ------------------- MainModel init")
    }

    private val receiverFragmentState = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            Log.e("FrView", "on receiverFragmentState")

            intent?.let {
                Log.e("FrView", "on intent not null")
                val fragmentState = intent.getSerializableExtra("FragmentState") as FragmentState
                Log.e("FrView", "state is ${fragmentState}")
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

    fun setCurrentState(state: FragmentState) {
        Log.e("9789"," scs $state")
        emitter.onNext(state)
    }

    @SuppressLint("ResourceType")
    fun getCardColor(): Int {
        return Color.parseColor(appContext.getString(R.color.colorCardBackground))
    }
}