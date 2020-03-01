package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.widget.ImageView

// Класс обертка для работы с Picasso
data class Avatar(
        val monsterImageView: ImageView
) {
    lateinit var pathMonster: String
}
