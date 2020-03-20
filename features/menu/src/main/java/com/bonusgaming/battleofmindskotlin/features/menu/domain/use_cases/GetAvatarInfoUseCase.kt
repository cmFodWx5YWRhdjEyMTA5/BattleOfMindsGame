package com.bonusgaming.battleofmindskotlin.main.domain.use_cases

import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.main.data.MenuRepository
import com.bonusgaming.battleofmindskotlin.main.domain.model.AvatarInfo

import javax.inject.Inject

@PerFragment
class GetAvatarInfoUseCase @Inject constructor(private val menuRepository: MenuRepository) {

    fun execute(): AvatarInfo {
        val avatarEntry = menuRepository.getAvatarEntry()
        val stickerEntry = menuRepository.getStickerEntryById(avatarEntry.idSticker)
        val imagePath = menuRepository.getImagesPath() + stickerEntry.path

        return AvatarInfo(imagePath, avatarEntry.nickName)
    }
}