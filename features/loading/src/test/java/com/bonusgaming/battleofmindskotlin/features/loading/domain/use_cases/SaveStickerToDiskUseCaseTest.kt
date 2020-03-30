package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.Sticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.getRandomMD5
import org.junit.Before
import org.junit.Ignore

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SaveStickerToDiskUseCaseTest {

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var saveStickerToDiskUseCase: SaveStickerToDiskUseCase

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        saveStickerToDiskUseCase = SaveStickerToDiskUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should call method in repository`() {
        //given
        val sticker = Sticker(getRandomMD5(), "somePath")
        val bitmapMock = mock(Bitmap::class.java)

        //when
        saveStickerToDiskUseCase.execute(sticker, bitmapMock)

        //then
        verify(loadingAssetsRepository).saveBitmapToDisk(sticker, bitmapMock)
    }
}