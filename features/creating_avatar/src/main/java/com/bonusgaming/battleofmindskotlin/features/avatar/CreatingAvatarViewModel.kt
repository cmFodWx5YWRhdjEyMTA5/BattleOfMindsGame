package com.bonusgaming.battleofmindskotlin.features.avatar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.avatar.domain.usecase.GetNextFragmentState
import com.firebase.ui.auth.IdpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

private const val MAX_NUMBER_IN_RANDOM_NAME = 1000
private const val MIN_LENGTH_NAME = 5
private const val MAX_LENGTH_NAME = 11

@PerFeature
class CreatingAvatarViewModel @Inject constructor(private val creatingAvatarModel: CreatingAvatarModel,
                                                  private val pathProvider: PathProvider,
                                                  private val nextState: GetNextFragmentState) : ViewModel() {

    private lateinit var currentAvatarPath: String
    private var nickName = "player_" + Random.nextInt(MAX_NUMBER_IN_RANDOM_NAME)
    private val listStickersMonsters = mutableListOf<Sticker>()
    private var monsterPointer = 0

    private val _initState = MutableLiveData<Boolean>()
    val initState: LiveData<Boolean> get() = _initState

    private val _nickNameLiveData = MutableLiveData<String>()
    val nickNameLiveData: LiveData<String> get() = _nickNameLiveData

    private val _isShortNameState = MutableLiveData<Boolean>()
    val isShortNameState: LiveData<Boolean> get() = _isShortNameState

    private val _allowCreateAvatar = MutableLiveData<Boolean>()
    val allowCreateAvatar: LiveData<Boolean> get() = _allowCreateAvatar

    private val _inflateAvatar = MutableLiveData<File>()
    val inflateAvatar: LiveData<File> get() = _inflateAvatar

    private val _fragmentIntentLiveData = MutableLiveData<FragmentState>()
    val fragmentIntentLiveData: LiveData<FragmentState> get() = _fragmentIntentLiveData

    init {
        Log.e("9977", "init CreatingAvatarVM")
        loadStickers {
            _initState.value = true
            onRandomButton()
        }
        _nickNameLiveData.value = nickName
    }

    private fun loadStickers(onLoad: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            listStickersMonsters.clear()
            val listS = creatingAvatarModel.getMonsters()
            listStickersMonsters.addAll(listS)
            withContext(Dispatchers.Main) {
                onLoad()
            }
        }
    }

    private fun inflateByPointer() {
        currentAvatarPath = listStickersMonsters[monsterPointer].path
        _inflateAvatar.value = File(pathProvider.getImagesPath() + currentAvatarPath)
    }

    fun onCorrectText(oldChar: CharSequence): CharSequence {
        return when {
            nickName.length <= MAX_LENGTH_NAME -> oldChar.replace(Regex("\\W"), "")
            else -> ""
        }
    }

    fun onTextChanged(oldText: CharSequence) {
        nickName = oldText.toString()
        _isShortNameState.value = oldText.length < MIN_LENGTH_NAME
    }

    fun onLeftButton() {
        monsterPointer--
        if (monsterPointer <= 0) monsterPointer = listStickersMonsters.size - 1
        inflateByPointer()
    }

    fun onRandomButton() {
        monsterPointer = (listStickersMonsters.indices).random()
        val randomPath = listStickersMonsters[monsterPointer].path
        _inflateAvatar.value = File(pathProvider.getImagesPath() + randomPath)
    }

    fun onRightButton() {
        monsterPointer++
        if (monsterPointer > listStickersMonsters.size - 1) monsterPointer = 0
        inflateByPointer()
    }

    fun onCreateButton() {
        _allowCreateAvatar.value = nickName.length >= MIN_LENGTH_NAME
    }

    fun onLoginSuccess(response: IdpResponse?) {

        viewModelScope.launch(Dispatchers.IO) {
            val uid = creatingAvatarModel.getUserUid()
            uid?.let {
                val avatarEntry = Avatar(nickName, listStickersMonsters[monsterPointer].id, uid)
                creatingAvatarModel.saveAvatar(avatarEntry)
                val savedAvatar = creatingAvatarModel.getAvatar()

                @Suppress("ControlFlowWithEmptyBody")
                if (avatarEntry != savedAvatar) {
                    //TODO something toast error
                }
                withContext(Dispatchers.Main) {
                    _fragmentIntentLiveData.value = nextState.execute()
                }
            }
        }
    }

    fun onLoginFailed(response: IdpResponse?) {
        Log.e("login", "failed ${response?.error?.message}")
    }
}
