package com.bonusgaming.battleofmindskotlin.creating_avatar

import androidx.annotation.IdRes

data class Avatar(
    @IdRes val bodyIdRes: Int, @IdRes val faceIdRes: Int,
    var pathBody: String, var pathFace: String
)