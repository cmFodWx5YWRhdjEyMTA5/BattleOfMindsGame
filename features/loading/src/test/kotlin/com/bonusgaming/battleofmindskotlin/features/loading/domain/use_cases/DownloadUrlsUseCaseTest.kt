package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bonusgaming.battleofmindskotlin.features.loading.RxRule
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.getRandomUrlStickerList
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DownloadUrlsUseCaseTest {

    @Rule
    @JvmField
    val rxRule:RxRule=RxRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var downloadUrlsUseCase: DownloadUrlsUseCase

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

    @Test(timeout = 5000)
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
                    assertEquals(expectedMessage, it.message)
                })
    }
}