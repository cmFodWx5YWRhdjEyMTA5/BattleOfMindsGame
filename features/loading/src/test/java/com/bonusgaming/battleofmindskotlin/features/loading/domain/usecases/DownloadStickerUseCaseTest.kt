package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import android.graphics.Bitmap
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.getRandomMD5
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DownloadStickerUseCaseTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var downloadStickerUseCase: DownloadStickerUseCase

    @Before
    fun setUp() {
        downloadStickerUseCase = DownloadStickerUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should call onDownload`() {
        //given
        val bitmap = mock(Bitmap::class.java)
        val urlSticker = UrlSticker("someLink", "someName", "1234", getRandomMD5())
        val onDownload: (bitmap: Bitmap) -> Unit = { /*NOP*/ }
        val onException: (url: String) -> Unit = { throw Exception("Should NOT called onException") }
        val onDownloadMock = mock(onDownload.javaClass)

        //when
        `when`(loadingAssetsRepository.downloadUrlStickerToDisk(urlSticker,
                onDownloadMock, onException)).then {

            onDownloadMock.invoke(bitmap)
        }
        downloadStickerUseCase.download(urlSticker, onDownloadMock, onException)

        //then
        verify(onDownloadMock).invoke(bitmap)
    }

    @Test
    fun `should call onException`() {
        //given
        val errorExpected = "some error"
        val urlSticker = UrlSticker("someLink", "someName", "1234", getRandomMD5())
        val onDownload: (bitmap: Bitmap) -> Unit = { /*NOP*/}
        val onException: (url: String) -> Unit = { throw Exception("Should NOT called onException") }
        val onExceptionMock = mock(onException.javaClass)

        //when
        `when`(loadingAssetsRepository.downloadUrlStickerToDisk(urlSticker,
                onDownload, onExceptionMock)).then {
            onExceptionMock.invoke(errorExpected)
        }
        downloadStickerUseCase.download(urlSticker, onDownload, onExceptionMock)

        //then
        verify(onExceptionMock).invoke(errorExpected)
    }

}