package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.db.StickerEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreatingAvatarViewModel : ViewModel() {

    private lateinit var avatar: Avatar

    val initState = MutableLiveData<Boolean>()

    @Inject
    lateinit var creatingAvatarModel: CreatingAvatarModel

    private var currentBody: String = ""
    private var currentFace: String = ""

    private val listBodies = mutableListOf<StickerEntry>()
    private val listEyes = mutableListOf<StickerEntry>()
    private val listMouths = mutableListOf<StickerEntry>()
    private val listMonsters = mutableListOf<StickerEntry>()


    init {
        App.appComponent.inject(this)
        loadStickers()
    }

    private fun getRandomFrom(list: List<StickerEntry>): String {
        return list[(list.indices).random()].path
    }


    fun fillAvatarRandom(avatar: Avatar) {
        //avatar.pathBody = getRandomFrom(listBodies)
        //avatar.pathMouth = getRandomFrom(listMouths)
        //avatar.pathEye = getRandomFrom(listEyes)
        avatar.pathMonster = getRandomFrom(listMonsters)

        creatingAvatarModel.inflateAvatar(avatar)
    }

    private fun loadStickers() {
        viewModelScope.launch(Dispatchers.IO) {
//            listBodies.clear()
//            listBodies.addAll(creatingAvatarModel.getBodies())
//            Log.e("loadstickers", "getbodies ${listBodies.size}")
//
//            listEyes.clear()
//            listEyes.addAll(creatingAvatarModel.getEyes())
//            Log.e("loadstickers", "listEyes ${listEyes.size}")
//
//            listMouths.clear()
//            listMouths.addAll(creatingAvatarModel.getMouths())
//            Log.e("loadstickers", "listMouths ${listMouths.size}")
//
//
            listMonsters.clear()
            listMonsters.addAll(creatingAvatarModel.getMonsters())
            Log.e("loadstickers", "listMouths ${listMonsters.size}")



            withContext(Dispatchers.Main) {
                initState.value = true
            }
        }
    }

}