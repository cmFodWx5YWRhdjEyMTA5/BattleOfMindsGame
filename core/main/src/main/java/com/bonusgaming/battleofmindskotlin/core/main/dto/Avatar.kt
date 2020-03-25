package com.bonusgaming.battleofmindskotlin.core.main.dto

import com.bonusgaming.battleofmindskotlin.core.main.contract.UserInfoContract


data class
Avatar(
        override val nickName: String,
        override val idSticker: Int,
        override val idFirebase: String)
    : UserInfoContract