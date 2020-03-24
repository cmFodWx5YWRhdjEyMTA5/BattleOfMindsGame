package com.bonusgaming.battleofmindskotlin.features.logo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.logo.domain.GetNextFragmentStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HelloViewModel @Inject  constructor(val nextFragmentStateUseCase: GetNextFragmentStateUseCase) : ViewModel() {

    private val _stateLiveData = MutableLiveData<FragmentState>()
    val stateLiveData: LiveData<FragmentState> get() = _stateLiveData

    fun onViewInit() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = nextFragmentStateUseCase.execute(2000)
            withContext(Dispatchers.Main) {
                _stateLiveData.value = state
            }
        }
    }

}
