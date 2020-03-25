package com.bonusgaming.battleofmindskotlin.features.menu.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.menu.domain.model.ClickType
import com.bonusgaming.battleofmindskotlin.features.menu.domain.model.AvatarInfo
import com.bonusgaming.battleofmindskotlin.features.menu.domain.use_cases.GetAvatarInfoUseCase
import com.bonusgaming.battleofmindskotlin.features.menu.domain.use_cases.GetFragmentStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MenuViewModel @Inject constructor(private val getAvatarUseCase: GetAvatarInfoUseCase,
                                        private val getFragmentStateUseCase: GetFragmentStateUseCase) : ViewModel() {

    private lateinit var avatarInfo: AvatarInfo

    private val _liveFragmentState: MutableLiveData<FragmentState> = MutableLiveData()
    val liveFragmentState: LiveData<FragmentState> get() = _liveFragmentState

    private val _avatarChanged: MutableLiveData<AvatarInfo> = MutableLiveData()
    val avatarChanged: LiveData<AvatarInfo> get() = _avatarChanged

    init {
        Log.e("9977","init MenuViewModel")
        viewModelScope.launch(Dispatchers.IO) {
            avatarInfo = getAvatarUseCase.execute()
            withContext(Dispatchers.Main) {
                _avatarChanged.value = avatarInfo
            }
        }
    }

    fun onStartClick() {
        _liveFragmentState.value = getFragmentStateUseCase.execute(ClickType.START)
    }

    fun onStatisticsClick() {
        _liveFragmentState.value = getFragmentStateUseCase.execute(ClickType.STATISTICS)
        //TODO show histograms for statistics
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("9977", "menu onCleared }")
    }
}