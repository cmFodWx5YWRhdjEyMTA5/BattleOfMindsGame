package com.bonusgaming.battleofmindskotlin.dbapi

interface DbApi {
    fun provideAvatarDao(): AvatarDao
    fun provideStickersDao(): StickerDao
}
