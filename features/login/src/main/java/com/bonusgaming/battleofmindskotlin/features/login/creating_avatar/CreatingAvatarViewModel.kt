package com.bonusgaming.battleofmindskotlin.features.login.creating_avatar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.dto.Avatar
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.firebase.ui.auth.IdpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

/*Класс отвечает за создания аватара*/
class CreatingAvatarViewModel @Inject constructor(private val creatingAvatarModel: CreatingAvatarModel,
                                                  private val pathProvider: PathProvider) : ViewModel() {

    private lateinit var currentAvatarPath: String
    private var nickName = generateRandomNickName()
    private val listMonsters = mutableListOf<Sticker>()
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
            listMonsters.clear()
            val listS=creatingAvatarModel.getMonsters()
            Log.e("777","listS ${listS.size}")
            listMonsters.addAll(listS)
            withContext(Dispatchers.Main) {
                onLoad()
            }
        }
    }

    private fun inflateByPointer() {
        currentAvatarPath = listMonsters[monsterPointer].path
        _inflateAvatar.value = File(pathProvider.getImagesPath() + currentAvatarPath)
    }

    private fun fillAvatarPrevious() {
        monsterPointer--
        if (monsterPointer <= 0) monsterPointer = listMonsters.size - 1
        inflateByPointer()
    }

    private fun fillAvatarNext() {
        monsterPointer++
        if (monsterPointer > listMonsters.size - 1) monsterPointer = 0
        inflateByPointer()
    }

    private fun generateRandomNickName(): String {
        return "player_" + Random.nextInt(10000)
    }

    private fun getRandomFrom(list: List<Sticker>): String {
        monsterPointer = (list.indices).random()
        return list[monsterPointer].path
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("9977", "creatingavatar onCleared }")
    }

    private fun getNextFragmentState() = FragmentState.MAIN

    fun onCorrectText(oldChar: CharSequence): CharSequence {
        return when {
            nickName.length <= 11 -> oldChar.replace(Regex("\\W"), "")
            else -> ""
        }
    }

    fun onTextChanged(oldText: CharSequence) {
        nickName = oldText.toString()
        _isShortNameState.value = oldText.length < 5
    }

    fun onLeftButton() {
        fillAvatarPrevious()
    }

    fun onRandomButton() {
        _inflateAvatar.value = File(pathProvider.getImagesPath() + getRandomFrom(listMonsters))
    }

    fun onRightButton() {
        fillAvatarNext()
    }

    fun onCreateButton() {
        _allowCreateAvatar.value = nickName.length >= 5
    }

    fun onLoginSuccess(response: IdpResponse?) {

        viewModelScope.launch(Dispatchers.IO) {
            val uid = creatingAvatarModel.getUserUid()
            uid?.let {
                val avatarEntry = Avatar(nickName, listMonsters[monsterPointer].id, uid)
                creatingAvatarModel.saveAvatar(avatarEntry)
                val savedAvatar = creatingAvatarModel.getAvatar()

                @Suppress("ControlFlowWithEmptyBody")
                if (avatarEntry != savedAvatar) {
                    //TODO something toast error
                }
                withContext(Dispatchers.Main) {
                    _fragmentIntentLiveData.value = getNextFragmentState()
                }
            }
        }
    }

    fun onLoginFailed(response: IdpResponse?) {
        Log.e("login", "failed ${response?.error?.message}")
    }
}