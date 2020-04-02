package com.bonusgaming.battleofmindskotlin.core.main.dto

import com.bonusgaming.battleofmindskotlin.core.main.contract.StickerInfoContract

data class Sticker(override val hashMD5: String, override val path: String) : StickerInfoContract {
    override var id: Int = 0
}