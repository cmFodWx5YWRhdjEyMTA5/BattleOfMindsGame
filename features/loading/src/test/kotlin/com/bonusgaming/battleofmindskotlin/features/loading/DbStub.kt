package com.bonusgaming.battleofmindskotlin.features.loading

import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
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