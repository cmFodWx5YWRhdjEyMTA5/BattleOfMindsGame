package com.bonusgaming.battleofmindskotlin.base_ui

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.mediator.StateMediator
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState

const val ACTION_CHANGE_FRAGMENT_STATE: String = "bonusgaming.change_fragment_state"
const val FRAGMENT_STATE_KEY = "FragmentState"

fun Fragment.nextState(state: FragmentState) {
    val intent = Intent(ACTION_CHANGE_FRAGMENT_STATE)
    intent.putExtra(FRAGMENT_STATE_KEY, state)
    Log.e("9789", " ${state} requireContext() ${requireContext()}")
    (requireActivity() as StateMediator).nextState(state)
}