package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.AvatarShape
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

    private val listFaces = mutableListOf<StickerEntry>()
    private val mapBodiesByShape = mutableMapOf<AvatarShape, List<StickerEntry>>()

    init {
        App.appComponent.inject(this)
        loadStickers()
    }

    private fun getRandomFacePath(): String {
        return listFaces[(0 until listFaces.size).random()].path
    }

    private fun getRandomBodyPath(): String {
        val shapeSize = AvatarShape.values().size
       // val randomShape = AvatarShape.values()[(0 until shapeSize).random()]
        //Log.e("avatar", "randomShape ${randomShape.name}")

        val listByShape = mapBodiesByShape[AvatarShape.FLUFFY]
        Log.e("avatar", "listShape size ${listByShape?.size}")
        var result = ""
        listByShape?.let {
            val randomListSize = it.size
            val randomEntry = (0 until randomListSize).random()
            result = it[randomEntry].path
        }
        return result
    }

    fun fillAvatarRandom(avatar: Avatar) {
        avatar.pathBody = getRandomBodyPath()
        avatar.pathFace = getRandomFacePath()
        creatingAvatarModel.inflateAvatar(avatar)
    }

    private fun loadStickers() {
        viewModelScope.launch(Dispatchers.IO) {
            listFaces.clear()
            listFaces.addAll(creatingAvatarModel.getAllFaces())
            mapBodiesByShape.putAll(creatingAvatarModel.getBodiesByShape())
            withContext(Dispatchers.Main) {
                initState.value = true
            }
        }
    }

}