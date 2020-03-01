package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.MainModel
import com.bonusgaming.battleofmindskotlin.db.AvatarEntry
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

/*Класс отвечает за создания аватара*/
class CreatingAvatarViewModel : ViewModel() {

    private var nickName = generateRandomNickName()

    private fun generateRandomNickName(): String {
        return "player_" + Random.nextInt(10000)
    }

    private lateinit var currentAvatar: Avatar

    val initState = MutableLiveData<Boolean>()
    val nickNameLiveData = MutableLiveData<String>()
    val isShortNameState = MutableLiveData<Boolean>()
    val allowCreateAvatar = MutableLiveData<Boolean>()

    val fragmentIntentLiveData = MutableLiveData<Intent>()

    @Inject
    lateinit var creatingAvatarModel: CreatingAvatarModel

    private val listMonsters = mutableListOf<StickerEntry>()
    private var monsterPointer = 0


    init {
        App.appComponent.inject(this)
        loadStickers()
        nickNameLiveData.value = nickName
    }

    fun onCorrectText(oldChar: CharSequence): CharSequence {
        return when {
            nickName.length <= 11 -> oldChar.replace(Regex("\\W"), "")
            else -> ""
        }

    }

    fun onTextChanged(oldText: CharSequence) {
        nickName = oldText.toString()
        isShortNameState.value = oldText.length < 5
    }


    private fun getRandomFrom(list: List<StickerEntry>): String {
        monsterPointer = (list.indices).random()
        return list[monsterPointer].path
    }

    fun fillAvatarRandom(avatar: Avatar) {
        currentAvatar = avatar
        avatar.pathMonster = getRandomFrom(listMonsters)

        creatingAvatarModel.inflateAvatar(avatar)
    }

    private fun loadStickers() {
        viewModelScope.launch(Dispatchers.IO) {
            listMonsters.clear()
            listMonsters.addAll(creatingAvatarModel.getMonsters())
            Log.e("loadstickers", "listMouths ${listMonsters.size}")
            withContext(Dispatchers.Main) {
                initState.value = true
            }
        }
    }

    fun onLeftButton() {
        fillAvatarPrevious()
    }

    fun onRandomButton() {
        fillAvatarRandom(currentAvatar)
    }


    fun onRightButton() {
        fillAvatarNext()
    }

    fun onCreateButton() {
        allowCreateAvatar.value = nickName.length >= 5
    }

    private fun fillAvatarPrevious() {
        monsterPointer--
        if (monsterPointer <= 0) monsterPointer = listMonsters.size - 1
        currentAvatar.pathMonster = listMonsters[monsterPointer].path
        creatingAvatarModel.inflateAvatar(currentAvatar)
    }

    private fun fillAvatarNext() {
        monsterPointer++
        if (monsterPointer > listMonsters.size - 1) monsterPointer = 0
        currentAvatar.pathMonster = listMonsters[monsterPointer].path
        creatingAvatarModel.inflateAvatar(currentAvatar)
    }


    fun onLoginSuccess(response: IdpResponse?) {

        creatingAvatarModel.getAvatar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.e("login", "error ${it}")
                }
                .doAfterNext {
                    fragmentIntentLiveData.value = getNextFragmentIntent()
                }.subscribe()

        viewModelScope.launch(Dispatchers.IO)
        {
            val uid = creatingAvatarModel.getUserUid()
            uid?.let {
                val avatarEntry = AvatarEntry(nickName, listMonsters[monsterPointer].id, uid)
                creatingAvatarModel.saveAvatar(avatarEntry)
            }

        }
    }

    fun onLoginFailed(response: IdpResponse?) {
        Log.e("login", "failed ${response?.error?.message}")

    }

    private fun getNextFragmentIntent() = Intent(MainModel.ACTION_CHANGE_FRAGMENT_STATE).also {
        it.putExtra("FragmentState", FragmentState.MAIN)
    }

}