package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.widget.ImageView

data class Avatar(
    val bodyImageView: ImageView, val faceImageView: ImageView
) {
    lateinit var pathBody: String
    lateinit var pathFace: String
}
