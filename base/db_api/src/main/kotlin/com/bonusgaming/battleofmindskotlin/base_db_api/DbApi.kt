package com.bonusgaming.battleofmindskotlin.base_db_api

interface DbApi {
    fun provideAvatarDao(): AvatarDao
    fun provideStickersDao(): StickerDao
}