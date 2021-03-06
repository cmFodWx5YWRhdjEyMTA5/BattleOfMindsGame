package com.bonusgaming.battleofmindskotlin.tools

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bonusgaming.battleofmindskotlin.FragmentState

const val ACTION_CHANGE_FRAGMENT_STATE: String = "bonusgaming.change_fragment_state"
const val FRAGMENT_STATE_KEY = "FragmentState"

fun Fragment.sendIntentForNextState(state: FragmentState){
    val intent = Intent(ACTION_CHANGE_FRAGMENT_STATE)
    intent.putExtra(FRAGMENT_STATE_KEY, state)
    Log.e("9789"," ${state} requireContext() ${requireContext()}")
    LocalBroadcastManager.getInstance(requireContext())
            .sendBroadcast(intent)
}