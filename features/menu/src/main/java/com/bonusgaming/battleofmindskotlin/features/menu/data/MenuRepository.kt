package com.bonusgaming.battleofmindskotlin.features.menu.data

import com.bonusgaming.battleofmindskotlin.base_db_api.AvatarDao
import com.bonusgaming.battleofmindskotlin.base_db_api.StickerDao
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import javax.inject.Inject

@PerFeature
class MenuRepository @Inject constructor(private val stickerDao: StickerDao,
                                         private val avatarDao: AvatarDao,
                                         private val pathProvider: PathProvider) {

    fun getAvatarEntry() = avatarDao.getAvatar()

    fun getStickerEntryById(id: Int) = stickerDao.getStickersById(id)

    fun getImagesPath() = pathProvider.getImagesPath()

}