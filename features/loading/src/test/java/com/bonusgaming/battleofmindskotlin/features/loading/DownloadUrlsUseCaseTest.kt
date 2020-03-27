package com.bonusgaming.battleofmindskotlin.features.loading

import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases.DownloadUrlsUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DownloadUrlsUseCaseTest {

    @Rule
    @JvmField
    val rule: RxRule = RxRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    lateinit var downloadUrlsUseCase: DownloadUrlsUseCase

    @Before
    fun setUp() {
        downloadUrlsUseCase = DownloadUrlsUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should return same list`() {
        //given
        val expectedList = getRandomUrlStickerList(100)

        //when
        `when`(loadingAssetsRepository.getStickerUrls())
                .thenReturn(Single.just(expectedList))

        //then
        downloadUrlsUseCase.execute({
            assertEquals(expectedList, it)
        }, { throw it })
    }

    @Test(timeout = 15000)
    fun `should throw error`() {
        //given
        val expectedMessage = "Some message error"

        //when
        `when`(loadingAssetsRepository.getStickerUrls())
                .thenReturn(Single.error(Exception(expectedMessage)))
        downloadUrlsUseCase.isRetryWhenEnabled = false

        //then
        downloadUrlsUseCase.execute({ throw Exception("Should throw error instead doSuccess") },
                {
                    println("error lalala dsfvbj")
                    assertEquals(expectedMessage, it.message)
                })

    }
}