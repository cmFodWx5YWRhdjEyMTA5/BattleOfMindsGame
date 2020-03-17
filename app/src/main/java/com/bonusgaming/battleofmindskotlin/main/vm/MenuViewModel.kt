package com.bonusgaming.battleofmindskotlin.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import javax.inject.Inject

@PerFragment
class MenuViewModel @Inject constructor() : ViewModel() {
    val liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()

    fun getLiveFragmentState(): LiveData<FragmentState> = liveFragmentState

    fun onStartClick() {
        liveFragmentState.value = FragmentState.LOADING
    }
}