package com.bonusgaming.battleofmindskotlin.core.main.dto

import com.bonusgaming.battleofmindskotlin.core.main.contract.UseInfoContract


data class
Avatar(
        override val nickName: String,
        override val idSticker: Int,
        override val idFirebase: String)
    : UseInfoContract