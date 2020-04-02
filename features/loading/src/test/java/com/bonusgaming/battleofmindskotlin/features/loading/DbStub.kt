package com.bonusgaming.battleofmindskotlin.features.loading

import com.bonusgaming.battleofmindskotlin.dbapi.AvatarDao
import com.bonusgaming.battleofmindskotlin.dbapi.DbApi
import com.bonusgaming.battleofmindskotlin.dbapi.StickerDao
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class DbStub : DbApi {
    override fun provideAvatarDao(): AvatarDao {
        return Mockito.mock(AvatarDao::class.java)
    }

    override fun provideStickersDao(): StickerDao {
        val stickerDaoMock = Mockito.mock(StickerDao::class.java)
        `when`(stickerDaoMock.getHashStickersList()).thenReturn(getSameHashListWithSize(100))
        return stickerDaoMock
    }
}