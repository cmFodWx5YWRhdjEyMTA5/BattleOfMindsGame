package com.bonusgaming.battleofmindskotlin.main.data

import com.bonusgaming.battleofmindskotlin.PathProvider
import com.bonusgaming.battleofmindskotlin.db.AvatarDao
import com.bonusgaming.battleofmindskotlin.db.StickerDao
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import javax.inject.Inject

@PerFragment
class MenuRepository @Inject constructor(private val stickerDao: StickerDao,
                                         private val avatarDao: AvatarDao,
                                         private val pathProvider: PathProvider) {

    fun getAvatarEntry() = avatarDao.getAvatar()

    fun getStickerEntryById(id: Int) = stickerDao.getStickersById(id)

    fun getImagesPath() = pathProvider.getImagesPath()

}