package com.bonusgaming.battleofmindskotlin.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bonusgaming.battleofmindskotlin.FragmentState

class MenuViewModel {
    val liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()

    fun getLiveFragmentState(): LiveData<FragmentState> = liveFragmentState

    fun onStartClick() {
        liveFragmentState.value = FragmentState.LOADING
    }
}