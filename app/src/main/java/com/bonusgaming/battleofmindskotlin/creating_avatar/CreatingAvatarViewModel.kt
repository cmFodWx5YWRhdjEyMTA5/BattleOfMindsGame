package com.bonusgaming.battleofmindskotlin.creating_avatar

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.MainContract
import javax.inject.Inject

class CreatingAvatarViewModel : ViewModel() {

    @Inject
    lateinit var creatingAvatarModel: CreatingAvatarModel

    private var currentBody: String = ""
    private var currentFace: String = ""

    fun fillAvatar(avatar: Avatar) {
        avatar.pathBody = currentBody
        avatar.pathFace = currentFace

    }
}