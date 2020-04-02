package com.bonusgaming.battleofmindskotlin.features.menu.domain.use_cases

import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.features.menu.data.MenuRepository
import com.bonusgaming.battleofmindskotlin.features.menu.domain.model.AvatarInfo
import dagger.Reusable

import javax.inject.Inject


@Reusable
class GetAvatarInfoUseCase @Inject constructor(private val menuRepository: MenuRepository) {

    fun execute(): AvatarInfo {
        val avatarEntry = menuRepository.getAvatarEntry()
        val stickerEntry = menuRepository.getStickerEntryById(avatarEntry.idSticker)
        val imagePath = menuRepository.getImagesPath() + stickerEntry.path

        return AvatarInfo(imagePath, avatarEntry.nickName)
    }
}