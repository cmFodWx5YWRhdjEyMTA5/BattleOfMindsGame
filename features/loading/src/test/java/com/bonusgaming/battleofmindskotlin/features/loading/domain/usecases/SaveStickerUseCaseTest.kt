package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.getRandomMD5
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SaveStickerUseCaseTest {
    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var saveStickers: SaveStickerUseCase

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        saveStickers = SaveStickerUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should call saveToDb method in repository`() {
        //given
        val sticker = Sticker(getRandomMD5(), "somePath")

        //when
        saveStickers.saveToDb(sticker)

        //then
        Mockito.verify(loadingAssetsRepository).addStickerToDb(sticker)
    }

    @Test
    fun `should call saveToDisk method in repository`() {
        //given
        val sticker = Sticker(getRandomMD5(), "somePath")
        val bitmapMock = Mockito.mock(Bitmap::class.java)

        //when
        saveStickers.saveToDisk(sticker, bitmapMock)

        //then
        Mockito.verify(loadingAssetsRepository).saveBitmapToDisk(sticker, bitmapMock)
    }
}