package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.FragmentState
import com.bonusgaming.battleofmindskotlin.MainModel
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import com.firebase.ui.auth.IdpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreatingAvatarViewModel : ViewModel() {

    private lateinit var currentAvatar: Avatar

    val initState = MutableLiveData<Boolean>()

    val fragmentIntentLiveData = MutableLiveData<Intent>()

    @Inject
    lateinit var creatingAvatarModel: CreatingAvatarModel

    private val listMonsters = mutableListOf<StickerEntry>()
    private var monsterPointer = 0


    init {
        App.appComponent.inject(this)
        loadStickers()
        creatingAvatarModel.printTest()
    }

    private fun getRandomFrom(list: List<StickerEntry>): String {
        return list[(list.indices).random()].path
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


    fun onRightButton() {
        fillAvatarNext()
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
        Log.e("login", "success ${response?.email}")
        fragmentIntentLiveData.value = getNextFragmentIntent()

    }

    fun onLoginFailed(response: IdpResponse?) {
        Log.e("login", "failed ${response?.error?.message}")

    }

    private fun getNextFragmentIntent() = Intent(MainModel.ACTION_CHANGE_FRAGMENT_STATE).also {
        it.putExtra("FragmentState", FragmentState.MAIN)
    }

}